/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pStrat

abstract sealed class TerrS
{
	def mch[T](fNone: () => T, fRiver: TerrSRiver => T, fCoast: TerrSCoast => T) = this match
	{
	   case r: TerrSRiver  => fRiver(r)	   
	   case c: TerrSCoast => fCoast(c)
       case _: TerrSNone => fNone
	}
	def srct: String
	//def tList = List(() => T, fRiver: TerrSRiver => T, fCoast: TerrSCoast => T)
	//def copy: TerrS = mch(() =>TerrSNone(), t => TerrSRiver(t.isFlowRight), t => TerrSCoast())
}

object TerrSNone 
{ def label = "None"
  //def apply(): TerrSNone = new TerrSNone
}

case class TerrSNone() extends TerrS
{ override def srct: String = "TerrSNone"
}

object TerrSRiverR 
{ def apply() = new TerrSRiver(true)
  //def left = new TerrSRiver(false)
  //def label = "River"
}

object TerrSRiverL
{ def apply() = new TerrSRiver(false)
  //def left = new TerrSRiver(false)
  //def label = "River"
}

case class TerrSRiver(var isFlowRight: Boolean/* = true*/) extends TerrS
{ override def srct: String = "TerrSRiver"
}

object TerrSCoast
{ def label = "Coast"
}

case class TerrSCoast(/*var offset: Double = 0*/) extends TerrS
{
   override def srct: String = "TerrSCoast"
}