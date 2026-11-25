# Лаборатори №4 — JUnit 5 нэгжийн тест

**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест  
**Оюутан:** Э.Цэгмид  
**Оюутны код:** B232270107

## Төслийн бүтэц

- Maven төсөл: [lab04-resubmission](lab04-resubmission/)
- Үндсэн код: `lab04-resubmission/src/main/java/mn/edu/must/sqat/GradeCalculator.java`
- Нэгжийн тест: `lab04-resubmission/src/test/java/mn/edu/must/sqat/GradeCalculatorTest.java`
- Эцсийн тестийн гаралт: `results/mvn-test.txt`
- Мутацийн тестийн гаралт: `results/mvn-test-mutant.txt`
- `target/` хавтас нь Maven-ийн build output тул Git-д commit хийхгүй. Root болон Maven төслийн `.gitignore` файлд бүртгэсэн.

## Үнэлгээний дүрэм

| Оноо | Үсгэн үнэлгээ |
|---:|:---:|
| 90–100 | A |
| 80–89.99 | B |
| 70–79.99 | C |
| 60–69.99 | D |
| 0–59.99 | F |

Нийлбэр оноо нь ирц 10, лаборатори 40, сорил 1 — 10, сорил 2 — 10, эцсийн шалгалт 30 гэсэн бүрэлдэхүүнтэй. Бүрэлдэхүүн тус бүр өөрийн зөвшөөрөгдөх мужид байх ёстой.

## Тестийн хамрах хүрээ

Нийт **14 тестийн метод** бичсэн. Үүнээс 12 нь `@Test`, 2 нь `@ParameterizedTest` annotation ашигласан. Параметрчилсэн тестүүдийн олон оролтыг тооцсоноор Maven нийт 26 тестийн тохиолдол ажиллуулсан.

Тестүүд дараах нөхцөлүүдийг шалгана:

- Онооны хамгийн бага болон хамгийн их утга: 0, 100.
- Үнэлгээний зааг: 90, 89.99, 60, 59.99.
- Сөрөг, 100-аас их, NaN болон хязгааргүй утга.
- `totalScore` функцийн зөв нийлбэр болон буруу бүрэлдэхүүн.
- `letterGrade` болон `totalScore` функцийн параметрчилсэн тест.
- AAA (Arrange–Act–Assert) дараалал болон `@DisplayName` ашиглалт.

## Ажиллуулах заавар

Төсөл Java 17 target хувилбарт хөрвүүлэгдэнэ. Maven төслийн хавтас руу орж дараах командыг ажиллуулна:

```bash
cd lab04-resubmission
mvn clean test
```

### Локал орчны хувилбарын мэдээлэл

Тестийг өөрийн Windows 11 орчинд дараах хувилбаруудаар ажиллуулсан.

**Java — `java -version`**

```text
openjdk version "25.0.5.1" 2026-11-17 LTS
OpenJDK Runtime Environment Microsoft-16059580 (build 25.0.5.1+2-LTS)
OpenJDK 64-Bit Server VM Microsoft-16059580 (build 25.0.5.1+2-LTS, mixed mode, sharing)
```

**Maven — `mvn -version`**

```text
Apache Maven 3.10.0 (c43a36b8d67be7e0805a411bc0898af1a51f5472)
Maven home: C:\apache-maven-3.10.0
Java version: 25.0.5.1, vendor: Microsoft, runtime: C:\Program Files\Microsoft\jdk-25.0.5.102-hotspot
Default locale: en_US, platform encoding: UTF-8, time zone: Asia/Ulaanbaatar
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

## Мутацийн туршилт

Мутацийн туршилтаар `GradeCalculator` классын `score >= 90.0` нөхцөлийг түр хугацаанд `score > 90.0` болгон өөрчилж тест ажиллуулсан. Ингэснээр яг 90 оноо авсан үед `A` үнэлгээ өгөх ёстой байтал `B` үнэлгээ буцаах алдаа үүссэн.

Мутацийг дараах хоёр тест илрүүлсэн:

1. `gradeBoundaryAtNinety` — `expected: <A> but was: <B>`
2. `mapsScoresToExpectedGrade(double, String)[2]` — `expected: <A> but was: <B>`

Мутацийн туршилтын үр дүн:

- Tests run: 26
- Failures: 2
- Errors: 0
- Skipped: 0
- Build: `BUILD FAILURE`

Энэ нь хүлээгдэж буй үр дүн бөгөөд тестүүд кодын буруу өөрчлөлтийг илрүүлж чадсаныг харуулж байна. Туршилтын дараа эх кодын нөхцөлийг `score >= 90.0` болгон сэргээж, тестийг дахин ажиллуулсан.

## Бодит туршилтын үр дүн

**Локал эцсийн тест:**

- Tests run: 26
- Failures: 0
- Errors: 0
- Skipped: 0
- Build: `BUILD SUCCESS`

**Локал мутацийн тест:**

- Tests run: 26
- Failures: 2
- Errors: 0
- Skipped: 0
- Build: `BUILD FAILURE` — хүлээгдэж буй үр дүн.

Туршилтын гаралтыг дараах файлуудад хадгалсан:

- `results/mvn-test.txt`
- `results/mvn-test-mutant.txt`

## Дүгнэлт

Энэ лабораторийн ажлаар JUnit 5 ашиглан `GradeCalculator` классын үйлдлүүдийг шалгах 14 тестийн метод бичиж, нийт 26 тестийн тохиолдол ажиллуулсан. Миний хувьд хамгийн сонирхолтой нь 90 онооны заагийг шалгасан тест байлаа. `>= 90.0` нөхцөлийг `> 90.0` болгон өөрчлөхөд яг 90 оноо авсан тохиолдолд буруу үнэлгээ буцааж байгааг мутацийн туршилтаар олж харсан. Энэ алдааг `gradeBoundaryAtNinety` болон `mapsScoresToExpectedGrade` тестүүд илрүүлсэн нь заагийн утгыг тусгайлан шалгах хэрэгтэйг ойлгуулсан. Мөн буруу болон хязгаараас гадуурх оролтуудыг шалгах нь програмын найдвартай ажиллагаанд чухал болохыг мэдэж авсан. Эцэст нь эх кодыг сэргээж дахин ажиллуулахад бүх 26 тест амжилттай болсон.