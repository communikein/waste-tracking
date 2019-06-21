
# This code has been designed to simulate and automate the outputs of a set of IoT devices that are part of the garbage collecting process. 
#The json file created by this python scrypt can be use to feed data in the third party blockchain from Qube-Os.

#importing object from the other class inside the project folder

from blockchain import Blockchain
from waste import Waste
from change_ownership import Change_OwnerShip
from treatment_plant import Treatment_Plant
from producer import Producer
from collector import Collector
from recycler import Recycler
from landfill import Landfill
from power import Power
import json
import requests

#The role of this method is to create the entities that will (when converted into json) populate the output file. The current configuration 
#is creating a minimal set of elements to provide a simple example on how to convert python dictionaries keys and values (contained in the 
#python classes) into json strings.

def main1():
 
 blockchain=Blockchain()
 #create a file, assign a name to it and start inserting strings of json data into it
 text_file = open("data.json", "w")
 text_file.write("[")
 #populate and create objects with sample data (please refer to the class named as the created object for having more detailed explanation of the class meaning).

 #populate the producers with sample data
 producers = ["Elisabetta", "Elia", "Phuong"]
 prod_list=[]
 for x in range (len(producers)):
  prod_list.append(Producer())
  prod_list[x].producer['producer_id']=producers[x]
  prod_list[x].convertJson()
  prod_list[x].printOnaFile(text_file)
 text_file.write("\n")

 #populate treatments plats with sample data
 treatments= ["Treatment1"]
 treat_list=[]
 for x3 in range (len(treatments)):
  treat_list.append(Treatment_Plant())
  treat_list[x3].treat_dict['treatment_plant_id']=treatments[x3]
  treat_list[x3].convertJson()
  treat_list[x3].printOnaFile(text_file)
 text_file.write("\n")

 #populate collectors plats with sample data
 collectors= ["Collector1"]
 collect_list=[]
 for x4 in range (len(collectors)):
  collect_list.append(Collector())
  collect_list[x4].col_dict['collector_id']=collectors[x4]
  collect_list[x4].convertJson()
  collect_list[x4].printOnaFile(text_file)
 text_file.write("\n")

 #populate wastes with sample data
 wastes = ["plastic", "metal", "glass", "paper", "organic"]
 waste_list= [] 
 waste_samples=2
 for x1 in range (waste_samples):
  waste_list.append(Waste())
  waste_list[x1].waste_dict['waste_id']=json.dumps(x1+1)
  waste_list[x1].waste_dict['waste_type']=wastes[x1%5]
  waste_list[x1].waste_dict['waste_volume']=x1%10
  waste_list[x1].waste_dict['waste_quality']='good'
  waste_list[x1].waste_dict['waste_parameters']=''
  waste_list[x1].waste_dict['treatment_type']='elaborate'
  waste_list[x1].waste_dict['recycled_quantity']=x1*2
  waste_list[x1].waste_dict['recycled_process_waste']=x1*0.5%100
  waste_list[x1].waste_dict['power_energy_production']=x1*2
  waste_list[x1].waste_dict['power_heating_levels']=x1%5
  waste_list[x1].waste_dict['landfill_water_parameters']=''
  waste_list[x1].waste_dict['waste_weight']=x1*0.5+1
  waste_list[x1].waste_dict['landfill_air_parameters']=''
  waste_list[x1].waste_dict['landfill_gas_parameters']=''
  waste_list[x1].convertJson()
  waste_list[x1].printOnaFile(text_file)
 text_file.write("\n")
 
 

 #populate changeofownership with sample data where the numeric values are created using a simple randomizer formula
 samples=1
 change_of_ownership=[]
 for x2 in range(samples):
  change_of_ownership.append(Change_OwnerShip())
  change_of_ownership[x2].owner_dict['prev_waste_id']=json.dumps(x2+1)
  change_of_ownership[x2].owner_dict['prev_owner_id']=producers[x2%len(producers)]
  change_of_ownership[x2].owner_dict['new_waste_id']=json.dumps(x2+2)
  change_of_ownership[x2].owner_dict['new_owner_id']=treatments[x2%len(treatments)]
  change_of_ownership[x2].owner_dict['coordinates']=''
  change_of_ownership[x2].owner_dict['date']=''
  change_of_ownership[x2].convertJson()
  change_of_ownership[x2].printOnaFile(text_file)
 text_file.write("\n")

 # Case 1: WASTE ELIMINATION
 #populate recycle with sample data
 recycler_list=[]
 recyclers= ["Recycle1"]
 for x4 in range (len(treatments)):
  recycler_list.append(Recycler())
  recycler_list[x4].rec_dict['recycler_id']=recyclers[x4]
  recycler_list[x4].convertJson()
  recycler_list[x4].printOnaFile(text_file)
 text_file.write("\n")

 # Case 2: LANDFILL
 landfills=["Landfill1"]
 landfill_list=[]
 for x5 in range (len(treatments)):
  landfill_list.append(Landfill())
  landfill_list[x5].land_dict['landfill_id']=landfills[x5]
  landfill_list[x5].convertJson()
  landfill_list[x5].printOnaFile(text_file)
 text_file.write("\n")

 # Case 3: POWER 
 powers=["Power1"]          
 power_list=[]
 for x6 in range (len(treatments)):
  power_list.append(Power())
  power_list[x6].power_dict['power_id']=powers[x6]
  power_list[x6].convertJson()
  power_list[x6].printOnaFile(text_file)
 text_file.write("\n")
 text_file.write("] ")
 text_file.close()

if __name__ == '__main__':
    main1()