import pandas as pd
import numpy as np
from nltk.corpus import stopwords
stop = stopwords.words('english')
pat = r'\b(?:{})\b'.format('|'.join(stop))

eksik_veriler = pd.read_csv("rows.csv").dropna(subset=["Product","Issue","State","ZIP code"])
print(eksik_veriler.isnull().sum())

eksik_veriler["Product"] = eksik_veriler["Product"].str.replace (r'[^\w\s]+', '').str.replace(pat, '')
eksik_veriler["Issue"] = eksik_veriler["Issue"].str.replace (r'[^\w\s]+', '').str.replace(pat, '')
eksik_veriler["Company"] = eksik_veriler["Company"].str.replace (r'[^\w\s]+', '').str.replace(pat, '')
eksik_veriler["State"] = eksik_veriler["State"].str.replace (r'[^\w\s]+', '').str.replace(pat, '')

#: Product (Ürün), Issue (Konu),
#Company (Şirket), State, Complaint ID, Zip Code.

print(eksik_veriler)
product = eksik_veriler.iloc[:1000000,1:2]
Issue = eksik_veriler.iloc[:1000000,3:4]
Company = eksik_veriler.iloc[:1000000,7:8]
State = eksik_veriler.iloc[:1000000,8:9]
ZipCode = eksik_veriler.iloc[:1000000,9:10]
ComplaintID = eksik_veriler.iloc[:1000000,17:18]
#df=eksik_veriler[["Product","Issue","Company","Complaint","ZIP code"]]
s=pd.concat([product,Issue,Company,State,ZipCode,ComplaintID],axis=1)
print(s)
# print(df.head())

csv = s.to_excel("1000binveri.xlsx")
print(eksik_veriler.isnull().sum())
