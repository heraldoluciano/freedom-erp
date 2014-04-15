alter trigger fnitrecebertgbu inactive
alter trigger fnitrecebertgau inactive
--select * from fnitreceber ir where ir.vlrapagitrec < 0.02 and ir.vlrapagitrec > 0
update fnitreceber ir set ir.vlrapagitrec=0 where ir.vlrapagitrec < 0.02 and ir.vlrapagitrec > 0
--select * from fnitreceber ir where ir.statusitrec='RL' and ir.vlrapagitrec = 0
update fnitreceber ir set ir.statusitrec='RP' where ir.vlrapagitrec = 0


alter trigger fnitrecebertgbu active
alter trigger fnitrecebertgau active

