package capstone.donworry.monthlyExpenseGoals.domain;

import lombok.Getter;

@Getter
public enum SpendingGoals {
    GOAL_20K(200_000),
    GOAL_30K(300_000),
    GOAL_40K(400_000),
    GOAL_50K(500_000),
    GOAL_70K(700_000),
    GOAL_100K(100_000),
    GOAL_120K(120_000),
    GOAL_150K(150_000);

    private final int amount;

    SpendingGoals(int amount) {
        this.amount = amount;
    }
}
