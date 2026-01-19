# Harel – Travel Policy Automation Test (Java + Selenium + TestNG)

הפרויקט מממש את תסריט הבדיקה שנתת עבור רכישת ביטוח נסיעות לחו"ל באתר הראל:
https://digital.harel-group.co.il/travel-policy

## טכנולוגיות
- Java 17
- Selenium 4
- TestNG
- WebDriverManager (ניהול אוטומטי של ChromeDriver)
- Maven

## איך להריץ
### דרישות
- JDK 17
- Maven
- Google Chrome מותקן

### הרצה רגילה
```bash
mvn test
```

### הרצה ב-Headless
```bash
mvn test -Dheadless=true
```

## מבנה הפרויקט
- `src/test/java/com/harel/pages` – Page Objects
- `src/test/java/com/harel/tests` – TestNG tests
- `src/test/java/com/harel/utils` – Utilities (Date, screenshots)
- `src/test/resources/testng.xml` – קונפיג TestNG + Listener

## הערות חשובות (אמיתיות)
- הדף הוא SPA עם JavaScript, ולכן חייבים לעבוד עם `WebDriverWait` ולא עם `Thread.sleep`.
- בחירת התאריכים נעשית דרך הקלדה לשדות התאריך (עם fallback ל-JS event dispatch) כדי להיות עמידה לשינויים ב-DatePicker.
- בדיקת "סה"כ ימים" מניחה שהמערכת מציגה 30 או 31 (תלוי אם סופרים כולל יום יציאה/חזרה). אם רוצים להקשיח – משנים את ה-assert.

## Git
כדי לעמוד בדרישה של source control:
```bash
git init
git add .
git commit -m "Initial automated test for Harel travel policy flow"
# ואז ליצור repo ב-GitHub/Bitbucket ולהוסיף remote + push
```
