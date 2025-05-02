import java.util.Random;
import java.util.Scanner;

//Интерфейс для обработки текста.
interface ITextProcessor {

    String processText(String inputText);
}

//Класс для подсчета гласных и согласных (Унаследованный_класс 1).
 
class LetterCounter implements ITextProcessor {
    protected static final String VOWELS = 
        "AEIOUYaeiouyАЕЁИОУЫЭЮЯаеёиоуыэюя";
    protected static final String CONSONANTS = 
        "BCDFGHJKLMNPQRSTVWXZbcdfghjklmnpqrstvwxzБВГДЖЗЙКЛМНПРСТФХЦЧШЩбвгджзйклмнпрстфхцчшщ";
    
    @Override
    public String processText(String inputText) {
        int[] counts = countLetters(inputText);
        System.out.println("Количество гласных: " + counts[0]);
        System.out.println("Количество согласных: " + counts[1]);
        return inputText; 
    }
    
//Подсчитывает количество гласных и согласных.

    protected int[] countLetters(String text) {
        int[] counts = new int[2];
        for (char ch : text.toCharArray()) {
            if (VOWELS.indexOf(ch) != -1) {
                counts[0]++;
            } else if (CONSONANTS.indexOf(ch) != -1) {
                counts[1]++;
            }
        }
        return counts;
    }
}

//Класс для добавления символов (Унаследованный_класс 2).

class LetterAdder extends LetterCounter {
    private final Random random = new Random();
    
    @Override
    public String processText(String inputText) {
        StringBuilder result = new StringBuilder(inputText);
        int[] counts = countLetters(inputText);
        int vowelCount = counts[0];
        int consonantCount = counts[1];

        while (vowelCount != consonantCount) {
            int index = random.nextInt(result.length() + 1);

            if (vowelCount < consonantCount) {
                char vowel = VOWELS.charAt(random.nextInt(VOWELS.length()));
                result.insert(index, vowel);
                vowelCount++;
            } else {
                char consonant = CONSONANTS.charAt(
                    random.nextInt(CONSONANTS.length()));
                result.insert(index, consonant);
                consonantCount++;
            }
        }

        return result.toString();
    }
}

//Фабрика для создания текстовых процессоров.

class TextProcessorFactory {

    public static ITextProcessor createProcessor(int type) {
        switch (type) {
            case 1:
                return new LetterCounter();
            case 2:
                return new LetterAdder();
            default:
                throw new IllegalArgumentException(
                    "Неверный тип процессора: " + type);
        }
    }
}

//Главный класс приложения.

public class Lab3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип процессора:");
        System.out.println("1. Подсчет букв");
        System.out.println("2. Добавление букв для балансировки");
        int choice = scanner.nextInt();
        scanner.nextLine();

        ITextProcessor processor;
        try {
            processor = TextProcessorFactory.createProcessor(choice);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Введите текст:");
        String inputText = scanner.nextLine();

        String result = processor.processText(inputText);
        if (processor instanceof LetterAdder) {
            System.out.println("Результат обработки: " + result);
        }
    }
}
