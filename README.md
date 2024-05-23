# Data Validator
[![Actions Status](https://github.com/in0mad/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/in0mad/java-project-78/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/a240157dd3c03f56c637/maintainability)](https://codeclimate.com/github/in0mad/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/a240157dd3c03f56c637/test_coverage)](https://codeclimate.com/github/in0mad/java-project-78/test_coverage)
[![Main-CI](https://github.com/in0mad/java-project-78/actions/workflows/main-CI.yml/badge.svg?branch=main)](https://github.com/in0mad/java-project-78/actions/workflows/main-CI.yml)

## About
Data Validator is a library that can be used to check the correctness of any input data.

## Run checkstyle

```bash
make lint
```

## Make JaCoCo Report

```bash
make report
```

## Launch the Test

```bash
make test
```

## Demo of the App

```java
Validator validator = new Validator(); // only once create Validator and reuse it after
```

### StringSchema

This schema validate String objects. To choose this schema write:

```java
StringSchema schema = validator.string();
```

StringSchema has three validation methods:
* __required()__ - makes the fields required and limits the possibility to use null or empty String.
* __minLength()__ - adds a minimum length constraint for the String. The String must be equal or longer than a specified number. Requires an integer parameter of minimum length.
* __contains()__ - adds a String content constraint. The String must contain a substring passed in the method parameter.

Usage example:

```java
StringSchema schema = validator.string().required().minLength(5).contains("hex");
schema.isValid(""); // false
schema.isValid("hex"); // false
schema.isValid("telxeh"); // false
schema.isValid("hexlet"); // true
```

### NumberSchema

This schema validate Integer objects. To choose this schema write:

```java
NumberSchema schema = validator.number();
```

NumberSchema has three validation methods:
* __required()__ - makes the fileds required and limits the possibility to use null.
* __positive()__ - adds a constraint to use negative numbers.
* __range()__ - adds a range constraint (inclusive). Requires two integer parameters of the first and the last numbers of range.

Usage example:

```java
NumberSchema schema = validator.number().required().positive().range(2, 7);
schema.isValid(-5); // false
schema.isValid(8); // false
schema.isValid(5); // true
schema.isValid(7); // true
```

### MapSchema

This schema validate Map objects. To schoose this schema write:

```java
MapSchema schema = validator.map();
```

MapSchema has three validation methods:
* __required()__ - makes the fields required and limits the possibility to use null.
* __sizeOf()__ - adds a map size constraint. The K-V count must be equal to the number passed in the method parameter.
* __shape()__ - adds constraints to map values. Accepts as a parameter a map of keys whose values need to be validated and schemas that would validate the values.

Usage example:

```java
Map<String, BaseSchema<Integer>> schemas = new HashMap<>();
schemas.put("first", validator.number());
schemas.put("second", validator.number().positive());
schemas.put("third", validator.number().positive().range(3, 8));

schema.shape(schemas);

Map<String, Integer> number1 = new HashMap<>();
number1.put("first", 8);
number1.put("second", null);
number1.put("third", 5);
assertTrue(mapSchema.isValid(number1));

Map<String, Integer> number2 = new HashMap<>();
number2.put("first", 6);
number2.put("second", -5);
number1.put("third", 2);
assertFalse(mapSchema.isValid(number2));
}
```

## The Multiple Runtime Version Manager *asdf*

On *nix and macOS to manage Java versions we recommend using asdf https://github.com/asdf-vm/asdf.
