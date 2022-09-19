package com.recipe.manager.enumvalidator;


import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


  public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private List<String> acceptedValues = Collections.emptyList();
    private boolean caseSensitive = true;

    @Override
    public void initialize(ValueOfEnum annotation) {
      caseSensitive = annotation.caseSensitive();
      acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
          .map(this::enumMapping)
          .map(v -> caseSensitive ? v : v.toLowerCase(Locale.ROOT))
          .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (value == null) {
        return true;
      }
      boolean valid = acceptedValues.contains(caseSensitive ? value : value.toLowerCase(Locale.ROOT));
      context.disableDefaultConstraintViolation();
      if (!valid) {
        context.buildConstraintViolationWithTemplate("must be any of " + acceptedValues)
            .addConstraintViolation();
      }
      return valid;
    }

    String enumMapping(Enum<?> enumItem) {
      if (enumItem instanceof TitleAware) {
        return ((TitleAware<?>) enumItem).getTitle();
      } else {
        return enumItem.name();
      }
    }
  }


