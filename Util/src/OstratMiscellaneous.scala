/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pParse._

trait IsType[A <: AnyRef]
{ def isType(obj: AnyRef): Boolean
  def asType(obj: AnyRef): A
  def optType(obj: AnyRef): Option[A] = ife(isType(obj), Some(asType(obj)), None)
}

object IsType
{
  implicit object AnyRefIsType extends IsType[AnyRef]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[AnyRef]
    override def asType(obj: AnyRef): AnyRef = obj.asInstanceOf[AnyRef]
  }
}

/** Directory path absolute. */
class DirPathAbs(val arrayUnsafe: Array[String])
{ /** The path as a string with the slash characters inserted */
  def str: String = arrayUnsafe.foldLeft("")(_ + "/" + _)

  override def toString: String = "DirPathAbs" + str.enParenth
}

object DirPathAbs
{
  implicit val showEv: Show[DirPathAbs] = new Show[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"
    override def strT(obj: DirPathAbs): String = obj.str
    override def syntaxDepth(obj: DirPathAbs): Int = 1
    override def showDec(obj: DirPathAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
      case ShowTyped | ShowStdTypedFields => typeStr + obj.str.enParenth
      case _ => obj.str
    }
  }

  implicit val unshowEv:Unshow[DirPathAbs] = new Unshow[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"

    override def fromExpr(expr: Expr): EMon[DirPathAbs] = expr match {
      case SlashToken(_) => Good(new DirPathAbs(Array[String]()))
      case PathToken(_, array) => Good(new DirPathAbs(array))
      case expr => expr.startPosn.bad("Not an absolute path")
    }
  }
}

sealed trait JustOrName[+T]

case class Just[T](value: T) extends JustOrName[T]

case class JustName[T](name: String) extends JustOrName[T]

case object Unknown extends JustOrName[Nothing]

object JustNone extends JustOrName[Nothing]