/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc

trait LunitLevel extends Int1Elem
{ def desigStr: String
}

object LunitSole extends LunitLevel
{ override val int1: Int = 1
  override def desigStr: String = "S"
  override def toString: String = "Individual Soldier"
}

object MCrew extends LunitLevel
{ override val int1: Int = 2
  override def desigStr: String = "0"
  override def toString: String = "Team"
}

object MTeam extends LunitLevel
{ override val int1: Int = 3
  override def desigStr: String = "0"
  override def toString: String = "Team"
}

object Squad extends LunitLevel
{ override val int1: Int = 4
  override def desigStr: String = "•"
  override def toString: String = "Team"
}

object Section extends LunitLevel
{ override val int1: Int = 5
  override def desigStr: String = "••"
  override def toString: String = "Section"
}

object Platoon extends LunitLevel
{ override val int1: Int = 6
  override def desigStr: String = "•••"
  override def toString: String = "Platoon"
}

object Echelon extends LunitLevel
{ override val int1: Int = 7
  override def desigStr: String = "••••"
  override def toString: String = "Echelon"
}

object Company extends LunitLevel
{ override val int1: Int = 8
  override def desigStr: String = "|"
  override def toString: String = "Company"
}

object Battalion extends LunitLevel
{ override val int1: Int = 9
  override def desigStr: String = "||"
  override def toString: String = "Battalion"
}

object Regiment extends LunitLevel
{ override val int1: Int = 10
  override def desigStr: String = "|||"
  override def toString: String = "Regiment"
}

object Brigade extends LunitLevel
{ override val int1: Int = 11
  override def desigStr: String = "X"
  override def toString: String = "Brigade"
}

object Division extends LunitLevel
{ override val int1: Int = 12
  override def desigStr: String = "XX"
  override def toString: String = "Division"
}

object Corps extends LunitLevel
{ override val int1: Int = 13
  override def desigStr: String = "XXX"
  override def toString: String = "Corps"
}

object FieldArmy extends LunitLevel
{ override val int1: Int = 14
  override def desigStr: String = "XXXX"
  override def toString: String = "Field Army"
}

object ArmyGroup extends LunitLevel
{ override val int1: Int = 15
  override def desigStr: String = "XXXXX"
  override def toString: String = "Army Group"
}