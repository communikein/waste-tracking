#importing the parent class block
from block import Block
#library used to convert python dictionaries into python
import json
from collections import OrderedDict
#dictionary with keys and values reflecting the json data needed for this entity in the blockchain
class Change_OwnerShip(Block):
 owner_dict = OrderedDict([
	('prev_waste_id',''),
 	('prev_owner_id',''),
 	('new_waste_id',''),
 	('new_owner_id',''),
 	('coordinates',''),
 	('date','')
	]
)
 def returnObject():
  	return self
 #assign a json converted string to the result defined in the parent class
 def convertJson(self):
  self.result=json.dumps(self.owner_dict)