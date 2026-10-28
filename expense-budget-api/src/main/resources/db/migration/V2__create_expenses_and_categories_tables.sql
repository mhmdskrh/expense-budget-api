create table expense_category(
    category_id serial primary key,
    name varchar not null,
    description varchar,
    user_id integer references _user(id) on delete cascade,
    constraint unique_category_per_user unique(user_id, name)
);

create table monthly_budget(
    monthly_budget_id serial primary key,
    category_id integer references expense_category(category_id) on delete cascade,
    month varchar not null,
    amount money not null,
    constraint unique_monthly_budget unique(category_id, month)
);

create table expense(
    expense_id serial primary key,
    category_id integer references expense_category(category_id) on delete set null,
    amount money not null,
    description varchar,
    expense_date date not null
);