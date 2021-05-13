from datetime import datetime

today = datetime.now()

# https://docs.python.org/3/library/datetime.html
print("starting: ",today.strftime("%Y-%m-%d %H:%M:%S"))