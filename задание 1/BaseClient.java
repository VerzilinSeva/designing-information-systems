package org.example;

public class BaseClient {
    protected static int idCounter = 0;
    protected int clientId;
    protected String name;
    protected String contactPerson;
    protected String inn;

    public BaseClient() {
        this.clientId = idCounter++;
    }

    public BaseClient(String name, String contactPerson, String inn) {
        this.clientId = idCounter++;
        this.name = validateName(name);
        this.contactPerson = validateContactPerson(contactPerson);
        this.inn = validateINN(inn);
    }

    public static String validateName(String name) {
        if (name == null || name.trim().length() < 2 || name.trim().length() > 100)
            throw new IllegalArgumentException("Некорректное название компании");
        return name;
    }

    public static String validateContactPerson(String contactPerson) {
        if (contactPerson == null) throw new IllegalArgumentException("Контактное лицо не может быть пустым");

        String trimmed = contactPerson.trim();
        String[] words = trimmed.split("\\s+");

        if (words.length < 2 || words.length > 3)
            throw new IllegalArgumentException("Контактное лицо должно состоять из 2-3 слов");

        StringBuilder normalized = new StringBuilder();
        for (String word : words) {
            if (!word.matches("[A-Za-zА-Яа-яЁё]{2,50}"))
                throw new IllegalArgumentException("Каждое слово контактного лица должно содержать только буквы (2-50 символов)");

            if (!word.isEmpty()) {
                if (normalized.length() > 0) normalized.append(" ");
                normalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
        }
        return normalized.toString();
    }

    public static String validateINN(String inn) {
        if (inn == null || !inn.matches("^\\d{10}$"))
            throw new IllegalArgumentException("Некорректный ИНН");
        return inn;
    }

    public int getClientId() {return clientId;}

    public String getName() {return name;}

    public String getContactPerson() {return contactPerson;}

    public String getInn() {return inn;}

    public void setClientId(int clientId) {this.clientId = clientId;}

    public void setName(String name) {this.name = name;}

    public void setContactPerson(String contactPerson) {this.contactPerson = contactPerson;}

    public void setInn(String inn) {this.inn = inn;}

    public String toShortString() {
        return "Краткая информация о клиенте: {" +
                "название организации:'" + name + '\'' +
                ", контактное лицо:'" + contactPerson + '\'' +
                ", ИНН:'" + inn + '\'' +
                '}';
    }
}
