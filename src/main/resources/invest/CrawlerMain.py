import sys
from FundDataCrawler import FundDataCrawler

code = sys.argv[1]

fundDataCrawler = FundDataCrawler()
fundData = fundDataCrawler.getFund(code)
print(fundData.size)