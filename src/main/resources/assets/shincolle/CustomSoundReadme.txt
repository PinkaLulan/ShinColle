******************* CUSTOM SOUND README ********************

1. put sound file (.ogg only) into the mod jar folder: assets/shincolle/sounds/

2. open the sound config file (config/shincolle/sounds.cfg) in your minecraft folder:
   
   format: shipID, idle, attack, hurt, dead, marry, knockback, item, feed, time 
   
   shipID    <-- is same with meta value of ship spawn egg
   idle      <-- idle sound rate, 0 = 0%, 100 = 100% play custom sound, otherwise general sound
   attack    <-- attack
   hurt      <-- hurt
   dead      <-- dead
   marry     <-- married idle
   knockback <-- knockback player when caressing
   item      <-- pick item
   feed      <-- feed
   time      <-- time keeping
  
  
   example:
   add custom sound to id 56 (Inazuma) and 2 (I-Class):

   56,50,100,100,100,20,0,0,80,0
   2,70,0,50,50,50,50,0,100,100


3. open "sounds.json" file in the mod jar file, add "-(shipID)" after sound name,
   like: "ship-hurt-56" = hurt sound for shipID = 56
   
   corresponding entry in sounds.json:
   idle      = "ship-idle"
   attack    = "ship-hit"
   hurt      = "ship-hurt"
   dead      = "ship-death"
   marry     = "ship-marry"
   knockback = "ship-knockback"
   item      = "ship-item"
   feed      = "ship-feed"
   time      = "ship-time0" ~ "ship-time23"

   json example:
   add id 56 (Inazuma) custom hurt sound with sound file hurt5601.ogg, hurt5602.ogg and hurt5603.ogg
   and time keeping sound at 7 p.m with sound file time5619.ogg:
   
	"ship-hurt-56": { "sounds": [
		"shincolle:hurt5601",
		"shincolle:hurt5602",
		"shincolle:hurt5603"
	]},
	
	"ship-time19-56": { "sounds": [
		"shincolle:time5619"
	]}


4. done
