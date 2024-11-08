# java-convenience-store-precourse

## 프로젝트 소개

**편의점 결제 시스템**은 사용자에게 할인 혜택과 재고 관리를 고려하여 최종 결제 금액을 계산하고 안내하는 시스템이다.   
사용자가 입력한 상품 가격과 수량을 기반으로 최종 결제 금액을 산출한다.  
프로모션 및 멤버십 할인 정책을 적용하여 효율적인 결제 경험을 제공한다.  
구매 내역과 할인 금액을 요약한 영수증을 출력하여 사용자에게 정확한 결제 정보를 전달한다.

## 주요 기능

- 상품 및 재고 관리
    - 각 상품의 가격과 재고 관리
    - 상품별로 프로모션 재고를 따로 설정하여 프로모션에 따른 재고 상태 관리
    - 구매 시 선택된 상품의 재고 수량 확인
    - 결제 후에는 결제된 수량만큼 재고에서 차감하여 최신 재고 상태를 유지
- 장바구니 기능
    - 고객이 원하는 상품과 수량을 장바구니에 추가
    - 장바구니에 담긴 상품의 전체 금액을 계산하여 결제 전 총 구매액 확인
- 프로모션 할인 적용
    - 특정 상품에 대해 N개 구매 시 1개 무료 증정 (1+1, 2+1)과 같은 프로모션 혜택을 제공
    - 프로모션은 지정된 기간에만 적용
    - 동일 상품에 여러 프로모션이 중복 적용되지 않음
    - 프로모션 재고가 우선 차감되며 재고가 부족할 경우 일부 수량에 대해서는 정가로 결제
- 멤버십 할인 적용
    - 프로모션 할인 적용 후 남은 금액에 대해 추가 할인 혜택을 제공
    - 멤버십 할인율은 30%이며 최대 8,000원 까지 할인 가능
- 예외 처리
    - 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException을 발생시키고 [ERROR]로 시작하는 에러 메시지 출력
    - 올바른 값이 입력될 때까지 재입력 받기
    - Exception이 아닌 명확한 예외 유형을 사용하여 다양한 예외 상황 처리
- 영수증 출력
    - 고객의 구매 내역과 할인 내역을 요약한 영수증 출력
    - 영수증을 보기 쉽게 정렬하여 고객이 구매 내역을 한눈에 확인할 수 있도록 함

## 기능 목록

1. ProductLoader : 상품 정보 가져오기
    - [x] 상품 정보를 Product 객체로 변환
    - [x] 각 줄을 ,로 분리하여 name, price, quantity, promotion 추출
    - [x] List<Product>로 반환
2. PromotionLoader : 프로모션 정보 가져오기
    - [ ] 프로모션 정보를 Promotion 객체로 변환
    - [x] 각 줄을 ,로 분리하여 name, buy, get, start_date, end_date 추출
    - [ ] List<Promotion>로 반환
3. Product : 상품 정보 및 재고 관리
    - [x] 상품명, 상품 가격, 상품 프로모션 반환
    - [x] 재고 감소
    - [ ] 프로모션 재고 여부 확인
    - [ ] 프로모션 재고 감소
4. CartItem : 장바구니에 담긴 단일 상품 정보 관리
    - [ ] 상품 정보와 구매 수량 반환
    - [x] 총 상품 가격 계산
5. Cart : 장바구니 관리 및 구매 내역 요약
    - [x] 장바구니에 아이템 추가
    - [ ] 장바구니에 담긴 모든 아이템 리스트 반환
    - [ ] 장바구니 전체 금액 계산
    - [x] 장바구니에 담긴 전체 수량 계산
    - [ ] 프로모션 적용하여 할인 금액 계산
6. Promotion : 프로모션 할인 정책 관리
    - [x] 해당 상품에 프로모션 적용 가능 여부 확인
    - [x] 장바구니 아이템에 대한 프로모션 할인 금액 계산
    - [x] 무료로 제공되는 증정 상품 수량 계산
7. MemebershipDiscount : 멤버십 할인 정책 관리
    - [ ] 멤버십 할인 금액 계산
    - [ ] 최대 할인 한도 반환
8. Receipt : 영수증 생성
    - [ ] 구매된 상품 정보 추가
    - [ ] 증정 상품 정보 추가
    - [ ] 할인 내역 설정
    - [ ] 최종 결제 금액 설정
9. PaymentSystem : 결제 처리 및 전체 흐름 관리
    - [ ] 결제 시스템 초기화
    - [ ] 상품을 장바구니에 추가
    - [ ] 프로모션 및 멤버십 할인 적용
    - [ ] 장바구니 결제 처리
    - [ ] 영수증 생성
10. InputView : 사용자 입력 처리
    - [x] 상품명 및 수량 입력 받기
    - [x] 멤버십 여부 입력 받기
    - [x] 추가 구매 여부 입력 받기
    - [x] 조건이 있는 경우 추가 확인 입력 받기
11. OutputView : 출력 처리
    - [ ] 생성된 영수증을 화면에 출력
    - [ ] 에러 메시지 출력
12. Validator : 유효성 검증
    - [ ] 재고값이 유효하지 않을 때 에러 메시지 출력 후 재입력 받기
        - [ ] 주문량이 양의 정수가 아닐 때 예외 처리
        - [ ] 주문량이 재고보다 적을 때 예외 처리
    - [ ] 입력값이 형식과 다를 때 에러 메시지 출력 후 재입력 받기