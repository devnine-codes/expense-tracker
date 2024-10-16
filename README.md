# pay-gateway 💰

**Pay Gateway**는 **Spring Boot**를 사용하여 구현한 소비 내역 분석 애플리케이션입니다.  
사용자는 **CSV 파일을 업로드**해 자신의 소비 데이터를 기록하고, **카테고리별/월별 지출 분석**을 수행할 수 있습니다.

---

## 🛠️ 기술 스택

### **Backend:**
- Java 17+
- Spring Boot 3.3.4
- Spring Data JPA (Hibernate)
- H2 Database (In-Memory)
- OpenCSV (CSV 파일 파싱)
- Maven

---

## 🎯 주요 기능

### **📂 CSV 파일 업로드:**
- 사용자가 카드사 또는 은행 내역 CSV 파일 업로드  
- 각 소비 내역을 자동으로 데이터베이스에 저장  

### **📊 지출 내역 관리 및 분석:**
- **모든 지출 내역 조회**
- **카테고리별 지출 합계** 제공
- **월별 지출 데이터 시각화**  
- **최고 지출 항목** 조회 기능 제공  

---

## 🔗 API 명세

### **CSV 파일 업로드 API**
- **POST /api/expenses/upload**: CSV 파일 업로드 및 데이터 저장

### **지출 내역 API**
- **POST /api/expenses**: 새로운 지출 내역 저장
- **GET /api/expenses**: 특정 기간 동안의 지출 내역 조회  
  - Query Params: `startDate`, `endDate` (YYYY-MM-DD 형식)  
- **GET /api/expenses/category-sum**: 카테고리별 지출 합계 조회  
  - Query Params: `startDate`, `endDate` (YYYY-MM-DD 형식)  
- **GET /api/expenses/monthly**: 월별 지출 데이터 조회  
  - Query Params: `startDate`, `endDate` (YYYY-MM-DD 형식)  
- **GET /api/expenses/highest**: 최고 지출 항목 조회  

---

## 🔒 보안 및 확장 가능성
- 현재는 기본적인 CSV 업로드와 분석 기능만 제공되지만, **JWT 인증**이나 **사용자별 지출 관리 기능**으로 확장 가능  
- PostgreSQL과 같은 **영구 데이터베이스로 전환**해 실제 서비스 운영 가능

---

## 실행 방법

### **Backend 실행:**
1. 프로젝트 루트 디렉터리에서 다음 명령어 실행:  
   ```bash
   mvn spring-boot:run
