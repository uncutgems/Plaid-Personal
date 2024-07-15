package com.application.mintplaid.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "transaction")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String plaidTransactionId;

    @JoinColumn(referencedColumnName = "plaid_account_id")
    @Column(name = "plaid_account")
    private String plaidAccount;

    private Date date;

    private Double amount;

    private Boolean pending;

    private String merchantName;

    private String paymentChannel;

    @Enumerated(EnumType.STRING)
    private SpendingCategory spendingCategory;

    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long linkId;

    public enum SpendingCategory {
        INCOME,
        TRANSFER_IN,
        TRANSFER_OUT,
        LOAN_PAYMENTS,
        BANK_FEES,
        ENTERTAINMENT,
        FOOD_AND_DRINK,
        GENERAL_MERCHANDISE,
        HOME_IMPROVEMENT,
        MEDICAL,
        PERSONAL_CARE,
        GENERAL_SERVICES,
        GOVERNMENT_AND_NON_PROFIT,
        TRANSPORTATION,
        TRAVEL,
        RENT_AND_UTILITIES,

    }

    public enum SubCategory {
        INCOME_DIVIDENDS,
        INCOME_INTEREST_EARNED,
        INCOME_RETIREMENT_PENSION,
        INCOME_TAX_REFUND,
        INCOME_UNEMPLOYMENT,
        INCOME_WAGES,
        INCOME_OTHER_INCOME,
        TRANSFER_IN_CASH_ADVANCES_AND_LOANS,
        TRANSFER_IN_DEPOSIT,
        TRANSFER_IN_INVESTMENT_AND_RETIREMENT_FUNDS,
        TRANSFER_IN_SAVINGS,
        TRANSFER_IN_ACCOUNT_TRANSFER,
        TRANSFER_IN_OTHER_TRANSFER_IN,
        TRANSFER_OUT_INVESTMENT_AND_RETIREMENT_FUNDS,
        TRANSFER_OUT_SAVINGS,
        TRANSFER_OUT_WITHDRAWAL,
        TRANSFER_OUT_ACCOUNT_TRANSFER,
        TRANSFER_OUT_OTHER_TRANSFER_OUT,
        LOAN_PAYMENTS_CAR_PAYMENT,
        LOAN_PAYMENTS_CREDIT_CARD_PAYMENT,
        LOAN_PAYMENTS_PERSONAL_LOAN_PAYMENT,
        LOAN_PAYMENTS_MORTGAGE_PAYMENT,
        LOAN_PAYMENTS_STUDENT_LOAN_PAYMENT,
        LOAN_PAYMENTS_OTHER_PAYMENT,
        BANK_FEES_ATM_FEES,
        BANK_FEES_FOREIGN_TRANSACTION_FEES,
        BANK_FEES_INSUFFICIENT_FUNDS,
        BANK_FEES_INTEREST_CHARGE,
        BANK_FEES_OVERDRAFT_FEES,
        BANK_FEES_OTHER_BANK_FEES,
        ENTERTAINMENT_CASINOS_AND_GAMBLING,
        ENTERTAINMENT_MUSIC_AND_AUDIO,
        ENTERTAINMENT_SPORTING_EVENTS_AMUSEMENT_PARKS_AND_MUSEUMS,
        ENTERTAINMENT_TV_AND_MOVIES,
        ENTERTAINMENT_VIDEO_GAMES,
        ENTERTAINMENT_OTHER_ENTERTAINMENT,
        FOOD_AND_DRINK_BEER_WINE_AND_LIQUOR,
        FOOD_AND_DRINK_COFFEE,
        FOOD_AND_DRINK_FAST_FOOD,
        FOOD_AND_DRINK_GROCERIES,
        FOOD_AND_DRINK_RESTAURANT,
        FOOD_AND_DRINK_VENDING_MACHINES,
        FOOD_AND_DRINK_OTHER_FOOD_AND_DRINK,
        GENERAL_MERCHANDISE_BOOKSTORES_AND_NEWSSTANDS,
        GENERAL_MERCHANDISE_CLOTHING_AND_ACCESSORIES,
        GENERAL_MERCHANDISE_CONVENIENCE_STORES,
        GENERAL_MERCHANDISE_DEPARTMENT_STORES,
        GENERAL_MERCHANDISE_DISCOUNT_STORES,
        GENERAL_MERCHANDISE_ELECTRONICS,
        GENERAL_MERCHANDISE_GIFTS_AND_NOVELTIES,
        GENERAL_MERCHANDISE_OFFICE_SUPPLIES,
        GENERAL_MERCHANDISE_ONLINE_MARKETPLACES,
        GENERAL_MERCHANDISE_PET_SUPPLIES,
        GENERAL_MERCHANDISE_SPORTING_GOODS,
        GENERAL_MERCHANDISE_SUPERSTORES,
        GENERAL_MERCHANDISE_TOBACCO_AND_VAPE,
        GENERAL_MERCHANDISE_OTHER_GENERAL_MERCHANDISE,
        HOME_IMPROVEMENT_FURNITURE,
        HOME_IMPROVEMENT_HARDWARE,
        HOME_IMPROVEMENT_REPAIR_AND_MAINTENANCE,
        HOME_IMPROVEMENT_SECURITY,
        HOME_IMPROVEMENT_OTHER_HOME_IMPROVEMENT,
        MEDICAL_DENTAL_CARE,
        MEDICAL_EYE_CARE,
        MEDICAL_NURSING_CARE,
        MEDICAL_PHARMACIES_AND_SUPPLEMENTS,
        MEDICAL_PRIMARY_CARE,
        MEDICAL_VETERINARY_SERVICES,
        MEDICAL_OTHER_MEDICAL,
        PERSONAL_CARE_GYMS_AND_FITNESS_CENTERS,
        PERSONAL_CARE_HAIR_AND_BEAUTY,
        PERSONAL_CARE_LAUNDRY_AND_DRY_CLEANING,
        PERSONAL_CARE_OTHER_PERSONAL_CARE,
        GENERAL_SERVICES_ACCOUNTING_AND_FINANCIAL_PLANNING,
        GENERAL_SERVICES_AUTOMOTIVE,
        GENERAL_SERVICES_CHILDCARE,
        GENERAL_SERVICES_CONSULTING_AND_LEGAL,
        GENERAL_SERVICES_EDUCATION,
        GENERAL_SERVICES_INSURANCE,
        GENERAL_SERVICES_POSTAGE_AND_SHIPPING,
        GENERAL_SERVICES_STORAGE,
        GENERAL_SERVICES_OTHER_GENERAL_SERVICES,
        GOVERNMENT_AND_NON_PROFIT_DONATIONS,
        GOVERNMENT_AND_NON_PROFIT_GOVERNMENT_DEPARTMENTS_AND_AGENCIES,
        GOVERNMENT_AND_NON_PROFIT_TAX_PAYMENT,
        GOVERNMENT_AND_NON_PROFIT_OTHER_GOVERNMENT_AND_NON_PROFIT,
        TRANSPORTATION_BIKES_AND_SCOOTERS,
        TRANSPORTATION_GAS,
        TRANSPORTATION_PARKING,
        TRANSPORTATION_PUBLIC_TRANSIT,
        TRANSPORTATION_TAXIS_AND_RIDE_SHARES,
        TRANSPORTATION_TOLLS,
        TRANSPORTATION_OTHER_TRANSPORTATION,
        TRAVEL_FLIGHTS,
        TRAVEL_LODGING,
        TRAVEL_RENTAL_CARS,
        TRAVEL_OTHER_TRAVEL,
        RENT_AND_UTILITIES_GAS_AND_ELECTRICITY,
        RENT_AND_UTILITIES_INTERNET_AND_CABLE,
        RENT_AND_UTILITIES_RENT,
        RENT_AND_UTILITIES_SEWAGE_AND_WASTE_MANAGEMENT,
        RENT_AND_UTILITIES_TELEPHONE,
        RENT_AND_UTILITIES_WATER,
        RENT_AND_UTILITIES_OTHER_UTILITIES,

    }

    public enum Status {
        None,
        Open,
        Settled,
    }
}
