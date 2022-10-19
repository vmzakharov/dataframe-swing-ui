# dataframe-swing-ui
a Swing UI app for interactively executing the dataframe-ec DSL scripts

### Sample script 1
```
2 + 2
```

### Sample script 2
```
a = 1
b = 2

if a > b then
  'oy'
else
  'ah'
endif
```

### Sample script 3
```
df = load("data.csv")
browse(df)

aggregated = aggregate(df, ('Bar', 'Baz'), ('Sum', 'Sum'))
browse(aggregated)

browse(select(df, 'Baz > 200.2'))
```

