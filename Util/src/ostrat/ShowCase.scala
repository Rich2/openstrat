/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The base trait for the persistence of Case classes, aka Product types */
abstract class ShowCase[R](val typeStr: String) extends ShowCompound[R]
{ def persistMems: List[Persist[_]]
  final override def syntaxDepth: Int = persistMems.map(_.syntaxDepth).max + 1  
}

/** Show type class for 1 parameter case classes. */
abstract class Show1[A1, R](typeStr: String, val fParam: R => (A1))(implicit ev1: Persist[A1]) extends ShowCase[R](typeStr)
{ final override def persistMems = List(ev1)
  def showSemi(obj: R): String = ev1.showComma(fParam(obj))
  def showComma(obj: R): String = ev1.show(fParam(obj))
}

/** Show type class for 2 parameter case classes. */
abstract class Show2[A1, A2, R](typeStr: String, val fParam: R => (A1, A2))(implicit ev1: Persist[A1], ev2: Persist[A2]) extends ShowCase[R](typeStr)
{ def persistMems = List(ev1, ev2)

  override def showSemi(obj: R): String =
  { val (a1, a2) = fParam(obj)
    ev1.showComma(a1) + "; " + ev2.showComma(a2)
  }

  override def showComma(obj: R): String =
  { val (a1, a2) = fParam(obj)
    ev1.show(a1) + ", " + ev2.show(a2)
  }
}

/** Show type class for 3 parameter case classes. */
abstract class Show3[A1, A2, A3, R](typeStr: String, val fParam: R => (A1, A2, A3))(implicit ev1: Persist[A1], ev2: Persist[A2],
    ev3: Persist[A3]) extends ShowCase[R](typeStr)
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

/** Show type class for 4 parameter case classes. */
abstract class Show4[A1, A2, A3, A4, R](typeStr: String, val fParam: R => (A1, A2, A3, A4))(implicit ev1: Persist[A1], ev2: Persist[A2],
   ev3: Persist[A3], ev4: Persist[A4]) extends ShowCase[R](typeStr)
{
  def persistMems = List(ev1, ev2, ev3, ev4)
  override def showSemi(obj: R): String = {
    val (p1, p2, p3, p4) = fParam(obj)
    ev1.show(p1).semicolonAppend(ev2.show(p2), ev3.show(p3), ev4.show(p4))
  }

  final override def showComma(obj: R): String =
  { val (p1, p2, p3, p4) = fParam(obj)
    ev1.show(p1).commaAppend(ev2.show(p2), ev3.show(p3), ev4.show(p4))
  }
}

class Show4Only[A1, A2, A3, A4, R](typeStr: String, fParam: R => (A1, A2, A3, A4))(implicit ev1: Persist[A1], ev2: Persist[A2],
   ev3: Persist[A3], ev4: Persist[A4]) extends Show4[A1, A2, A3, A4, R](typeStr, fParam) with ShowOnly[R]
