import sys
from FundDataCrawler import FundDataCrawler
from FundDecision import FundDecision

fundDataCrawler = FundDataCrawler()
fundDecision = FundDecision()

code = sys.argv[1]
date = sys.argv[2]

fundData = fundDataCrawler.readData(code)
df = fundDecision.getData(fundData, date, date)
print(df['收盘价'][0], df['涨跌幅'][0])