#importing the parent class block
from block import Block
#library used to convert python dictionaries into python
import json
from collections import OrderedDict
#simple class to assign the waste keys and values that need to be stored in the blockchain
class Waste (Block):
 waste_dict = OrderedDict([
	('waste_id',''),
 	('waste_type',''),
 	('waste_weight',''),
 	('waste_volume',''),
 	('waste_quality',''),
 	('waste_parameters',''),
 	('treatment_type',''),
 	('recycled_quantity',''),
 	('recycled_process_waste',''),
 	('power_energy_production',''),
 	('power_heating_levels',''),
 	('landfill_water_parameters',''),
 	('landfill_air_parameters',''),
 	('landfill_gas_parameters','')]
	)
 def returnWaste():
		return self
 def returnWasteDict():
 		return waste_dict
 #assign a json converted string to the result defined in the parent class 
 def convertJson(self):
  self.result=json.dumps(self.waste_dict)