/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The base trait for the persistence of Case classes, aka Product types */
abstract class ShowCase[R](typeSym: Symbol) extends ShowCompound[R](typeSym)
{ def persistMems: List[Persist[_]]
  final override def syntaxDepth: Int = persistMems.map(_.syntaxDepth).max + 1  
}

/** Persistence class for 3 parameter case classes. */   
abstract class Show3[A1, A2, A3, R](typeSym: Symbol, val fParam: R => (A1, A2, A3))(implicit ev1: Persist[A1], ev2: Persist[A2],
    ev3: Persist[A3]) extends ShowCase[R](typeSym)
{
  final override def persistMems = List(ev1, ev2, ev3)
  
  final override def showSemi(obj: R): String =
  { val (p1, p2, p3) = fParam(obj)
    ev1.show(p1).semicolonAppend(ev2.showComma(p2), ev3.showComma(p3))
  }

  final override def showComma(obj: R): String =
  { val (p1, p2, p3) = fParam(obj)
    ev1.show(p1).commaAppend(ev2.show(p2), ev3.show(p3))
  }
}