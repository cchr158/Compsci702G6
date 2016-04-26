import hashlib
import sys

name = sys.argv[1]
"""
for name in names:
	temp = hashlib.sha256(name.encode("UTF-8")).hexdigest()
	if list(temp)[0]>='0' and list(temp)[0]<='9':
		temp+='a'
		temp = temp[::-1]
	print(name,temp)
	print()
	"""
temp = hashlib.sha256(name.encode("UTF-8")).hexdigest()
if list(temp)[0]>='0' and list(temp)[0]<='9':
	temp+='a'
	temp = temp[::-1]
print(name,temp)