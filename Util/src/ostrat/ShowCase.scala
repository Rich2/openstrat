/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The base trait for the persistence of Case classes, aka Product types */
trait ShowCase[R]/*(val typeStr: String)*/ extends ShowCompound[R]
{ def showMems: Arr[Show[_]]
  final override def syntaxDepth: Int = showMems.map(_.syntaxDepth).max + 1
}

/** Show type class for 1 parameter case classes. */
abstract class Show1[A1, R](val typeStr: String, val fParam: R => (A1))(implicit ev1: Show[A1]) extends ShowCase[R]//(typeStr)
{ final override def showMems: Arr[Show[_]] = Arr(ev1)
  def showSemi(obj: R): String = ev1.showComma(fParam(obj))
  def showComma(obj: R): String = ev1.show(fParam(obj))
}

class Show1Only[A1, R](typeStr: String, fParam: R => A1)(implicit ev1: Persist[A1]) extends Show1[A1, R](typeStr,fParam) with ShowOnly[R]

/** Show type class for 2 parameter case classes. */
abstract class Show2[A1, A2, R](val typeStr: String, val fParam: R => (A1, A2))(implicit ev1: Show[A1], ev2: Show[A2]) extends ShowCase[R]//(typeStr)
{ final override def showMems: Arr[Show[_]] = Arr(ev1, ev2)

  override def showSemi(obj: R): String =
  { val (a1, a2) = fParam(obj)
    ev1.showComma(a1) + "; " + ev2.showComma(a2)
  }

  override def showComma(obj: R): String =
  { val (a1, a2) = fParam(obj)
    ev1.show(a1) + ", " + ev2.show(a2)
  }
}

class Show2Only[A1, A2, R](typeStr: String, fParam: R => (A1, A2))(implicit ev1: Persist[A1], ev2: Persist[A2]) extends Show2[A1, A2, R](typeStr,
  fParam) with ShowOnly[R]

/** Show type class for 3 parameter case classes. */
abstract class Show3[A1, A2, A3, R](val typeStr: String, val fParam: R => (A1, A2, A3), opt3: Option[A3] = None, opt2: Option[A2] = None,
    opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]) extends ShowCase[R]//(typeStr)
{ override def showMems: Arr[Show[_]] = Arr(ev1, ev2, ev3)
  
  final override def showSemi(obj: R): String =
  { val (p1, p2, p3) = fParam(obj)
    ev1.show(p1).semicolonAppend(ev2.showComma(p2), ev3.showComma(p3))
  }

  final override def showComma(obj: R): String =
  { val (p1, p2, p3) = fParam(obj)
    ev1.show(p1).commaAppend(ev2.show(p2), ev3.show(p3))
  }
}

class Show3Only[A1, A2, A3, R](typeStr: String, fParam: R => (A1, A2, A3))(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]) extends
  Show3[A1, A2, A3, R](typeStr, fParam) with ShowOnly[R]

/** Show type class for 4 parameter case classes. */
abstract class Show4[A1, A2, A3, A4, R](val typeStr: String, val fParam: R => (A1, A2, A3, A4))(implicit ev1: Persist[A1], ev2: Persist[A2],
  ev3: Persist[A3], ev4: Persist[A4]) extends ShowCase[R]//(typeStr)
{
  final override def showMems = Arr(ev1, ev2, ev3, ev4)
  override def showSemi(obj: R): String = {
    val (p1, p2, p3, p4) = fParam(obj)
    ev1.show(p1).semicolonAppend(ev2.show(p2), ev3.show(p3), ev4.show(p4))
  }

  final override def showComma(obj: R): String =
  { val (p1, p2, p3, p4) = fParam(obj)
    ev1.show(p1).commaAppend(ev2.show(p2), ev3.show(p3), ev4.show(p4))
  }
}

class Show4Only[A1, A2, A3, A4, R](typeStr: String, fParam: R => (A1, A2, A3, A4))(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3],
  ev4: Persist[A4]) extends Show4[A1, A2, A3, A4, R](typeStr, fParam) with ShowOnly[R]
