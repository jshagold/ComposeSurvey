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


        
✅ **기능 요약**

>1 설문 불러오기
>* 로컬 JSON 파일(assets/)에서 설문 목록 불러오기
>* Survey 객체로 파싱 (kotlinx.serialization 사용)
>
>2 설문 시작
>* 각 설문 항목 클릭 시 질문 화면으로 이동
>* 질문 타입에 따라 다른 UI 렌더링
>
>3 질문 타입 지원
>* 단답형 (text)
>* 객관식 단일 선택 (single_choice)
>* 객관식 다중 선택 (multiple_choice)
>* 슬라이더 (slider)
>* (선택) 날짜 선택, 시간 선택, 척도 (Likert scale)
>
>4 응답 저장
>* ViewModel에서 질문 ID → 응답값 형태로 상태 저장
>* 화면 회전 시 응답값 유지 (rememberSaveable)
>
>5 제출 및 결과 요약
>* 모든 질문에 답변을 완료하면 요약 화면으로 이동
>* 사용자 응답 전체를 정리하여 보여줌
        


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
      "question": "현재 직종은?",
      "options": ["학생", "구직자", "무직"]
    },
    {
      "id": "language",
      "type": "multiple_choice",
      "question": "사용하는 언어는?",
      "options": ["Kotlin", "Java", "Python"]
    },
    {
      "id": "experience",
      "type": "slider",
      "question": "경력 연차를 선택해주세요.",
      "min": 0,
      "max": 10
    },
    {
      "id": "level",
      "type": "likert_scale",
      "question": "본인의 실력을 평가해 주세요.",
      "0": "매우 못함",
      "1": "못함",
      "2": "보통",
      "3": "잘함",
      "4": "매우 잘함",
    },
  ]
}
