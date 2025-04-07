# 📋 ComposeSurvey

Jetpack Compose 기반 설문 응답 앱  
로컬 JSON 파일로 정의된 설문지를 동적으로 불러오고,  
사용자가 다양한 질문에 응답하여 결과를 요약 확인할 수 있습니다.  

---

## ✨ 주요 기능

- ✅ JSON 파일 기반 설문 정의 및 로딩
- ✅ 단답형, 체크(Check/Radio), Slide 등 다양한 질문 유형 지원
- ✅ Jetpack Compose 기반 동적 UI 렌더링
- ✅ ViewModel + StateFlow 기반 상태 관리 (MVVM 아키텍처)
- ✅ 응답 상태 유지 및 결과 요약 기능

---

## 🖥️ 스크린샷

---

## 🧠 기술 스택

| 분류       | 기술                     |
|------------|--------------------------|
| Language   | Kotlin                   |
| UI         | Jetpack Compose, Material3 |
| Architecture | MVVM, ViewModel, StateFlow |
| Data       | JSON, Room (선택사항) |
| 기타       | rememberSaveable, Navigation-Compose |

---

## 🧩 설문 JSON 구조 예시

```json
{
  "title": "개발자 설문조사",
  "questions": [
    {
      "id": "name",
      "type": "text",
      "question": "당신의 이름은?",
      "required": true
    },
    {
      "id": "language",
      "type": "single_choice",
      "question": "자주 사용하는 언어는?",
      "options": ["Kotlin", "Java", "Python"]
    },
    {
      "id": "experience",
      "type": "slider",
      "question": "경력 연차를 선택해주세요.",
      "min": 0,
      "max": 10
    }
  ]
}
