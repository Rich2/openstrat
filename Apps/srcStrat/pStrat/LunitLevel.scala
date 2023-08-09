/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pStrat
import geom._

/** Military land unit level of organisation. */
trait LunitLevel extends Int1Elem
{ def desigStr: String

  def drawables: RArr[Drawable]
}

/** 1 million - 3 million. */
object TheatreArmy extends LunitLevel
{ override val int1: Int = 14
  override def desigStr: String = "XXXX"
  override def toString: String = "Field Army"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-5, 5, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 500000 - 1 million men. */
object ArmyGroup extends LunitLevel
{ override val int1: Int = 15
  override def desigStr: String = "XXXXX"
  override def toString: String = "Army Group"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-4, 2, 4){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 200000 - 500000. 1914 German 1st Army 320000. */
object FieldArmy extends LunitLevel
{ override val int1: Int = 14
  override def desigStr: String = "XXXX"
  override def toString: String = "Field Army"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-3, 3, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 100000 - 200000. */
object FieldArmette extends LunitLevel
{ override val int1: Int = 14
  override def desigStr: String = "XXX|"
  override def toString: String = "Field Army"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-3, 3, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 50000 - 100000 men. A large Corps or a small field army. */
object Corps extends LunitLevel
{ override val int1: Int = 13
  override def desigStr: String = "XXX"
  override def toString: String = "Corps"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-2, 2, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 20000 - 50000 men. A small corps. */
object Corpette extends LunitLevel
{ override val int1: Int = 13
  override def desigStr: String = "XX|"
  override def toString: String = "Corps"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-2, 2, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 10000 - 20000 men. World War 1 16000 -18000. */
object Division extends LunitLevel
{ override val int1: Int = 12
  override def desigStr: String = "XX"
  override def toString: String = "Division"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-1, 1, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 5000 - 10000 men. A small division or large brigade. Roman legion 6600 fighting men + 1320 slaves. */
object Divisette extends LunitLevel
{ override val int1: Int = 12
  override def desigStr: String = "X|"
  override def toString: String = "Divisette"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-1, 1, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

/** 2000 - 5000 men. Napoleonic Brigades tended to be 2000 to 3000 men. */
object Brigade extends LunitLevel
{ override val int1: Int = 11
  override def desigStr: String = "X"
  override def toString: String = "Brigade"
  override def drawables: RArr[Drawable] = RArr(Cross.diag.scale(1.5))
}

/** 1000 - 2000 men. Small brigade, regiment or very large battalion. */
object Regiment extends LunitLevel
{ override val int1: Int = 10
  override def desigStr: String = "|||"
  override def toString: String = "Regiment"
  override def drawables: RArr[Drawable] = RArr(iToMap(-2, 2, 2){ i => LineSeg(0, -9.75, 0, 0.75).slateX(i) })
}

/** 500 - 1000 men. Battalion. Roman Cohort 600: 480 fighting men + 120 slaves. */
object Battalion extends LunitLevel
{ override val int1: Int = 9
  override def desigStr: String = "||"
  override def toString: String = "Battalion"
  override def drawables: RArr[Drawable] = RArr[Drawable](LineSeg(0, -9.75, 0, 0.75))
}

/** 200 - 500 men. Small Battalion or large company. */
object Battalette extends LunitLevel
{ override val int1: Int = 9
  override def desigStr: String = "||"
  override def toString: String = "Battalion"
  override def drawables: RArr[Drawable] = RArr[Drawable](LineSeg(0, -9.75, 0, 0.75))
}

/** 100 - 200 men. */
object Company extends LunitLevel
{ override val int1: Int = 8
  override def desigStr: String = "|"
  override def toString: String = "Company"
  override def drawables: RArr[Drawable] = RArr(LineSeg(0, -9.75, 0, 0.75))
}

/** 50 - 100 men. Roman Century 80 fighting men + 20 slaves. */
object Echelon extends LunitLevel
{ override val int1: Int = 7
  override def desigStr: String = "••••"
  override def toString: String = "Echelon"
  override def drawables: RArr[Drawable] = iToMap(-3, 3, 2){ i => Circle(0.5).scale(1.5).slateX(i) }
}

/** 20 to 50 men. */
object Platoon extends LunitLevel
{ override val int1: Int = 6
  override def desigStr: String = "•••"
  override def toString: String = "Platoon"
  override def drawables: RArr[Drawable] = iToMap(-2, 2, 2){ i => Circle(0.5).scale(1.5).slateX(i) }
}

/** 10 - 20 men. Platoon section, or large infantry squad. Given the conflicting terms around this size of unit, a new term was created.  */
object ZugSect extends LunitLevel
{ override val int1: Int = 5
  override def desigStr: String = "••"
  override def toString: String = "Section"
  override def drawables: RArr[Drawable] = iToMap(-1, 1, 2){ i => Circle(0.5).scale(1.5).slateX(i) }
}

/** 6 - 10 men. Small squad.  */
object Squadette extends LunitLevel
{ override val int1: Int = 5
  override def desigStr: String = "•"
  override def toString: String = "Squad"
  override def drawables: RArr[Drawable] = RArr(Circle(0.5))
}

/** 3 - 5 men. A fire team, vehicle. */
object FireTeam extends LunitSoleLike
{ override val int1: Int = 3
  override def desigStr: String = "ØØØ"
  override def toString: String = "FireTeam"
  override def drawables: RArr[Drawable] = RArr(iToMap(-2, 2, 2){ i => dashDrawable.slateX(i) })
}

/** 2 soldiers including "Fire and Maneuver teams. */
object FirePair extends LunitSoleLike
{ override val int1: Int = 2
  override def desigStr: String = """ØØ"""
  override def toString: String = "FirePair"
  override def drawables: RArr[Drawable] = RArr(iToMap(-1, 1, 2){ i => dashDrawable.slateX(i) })
}

trait LunitSoleLike extends LunitLevel
{ def dashDrawable: LineSeg = LineSeg(-0.25, 0, 0.25, 0)
}

/** Individual soldier. */
object LunitSole extends LunitSoleLike
{ override val int1: Int = 1
  override def desigStr: String = """Ø"""
  override def toString: String = "Individual Soldier"
  override def drawables: RArr[Drawable] = RArr(dashDrawable)
}