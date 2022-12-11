package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import jdk.jfr.Name;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(shift);
        String localDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return localDate;
    }

    public static String generateCity(String locale) {
        Random random= new Random();
        City cities=new City();
        String city = cities.getCities()[random.nextInt(cities.getCities().length)];
        return city;
    }


    public static String generateName(String locale) {
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
