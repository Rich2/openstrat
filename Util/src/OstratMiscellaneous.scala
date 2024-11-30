/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Not entirely sure what this type class is for */
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
  
  def / (newDir: String): DirPathAbs = DirPathAbs(arrayUnsafe.appended(newDir))
  
  def noExistStr: String = str -- "Doesn't exist"
  def notDirStr: String = str -- "is not a directory"
}

object DirPathAbs
{
  implicit val showEv: Show[DirPathAbs] = new Show[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"
    override def strT(obj: DirPathAbs): String = obj.str
    override def syntaxDepth(obj: DirPathAbs): Int = 1
    override def show(obj: DirPathAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
      case ShowTyped | ShowStdTypedFields => typeStr + obj.str.enParenth
      case _ => obj.str
    }
  }

  implicit val unshowEv:Unshow[DirPathAbs] = new Unshow[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"

    override def fromExpr(expr: Expr): ExcMon[DirPathAbs] =  expr match {
      case SlashToken(_) => Succ(new DirPathAbs(Array[String]()))
      case PathToken(_, array) => Succ(new DirPathAbs(array))
      case expr => expr.failExc("Not an absolute path")
    }
  }
}

sealed trait JustOrName[+T]

case class Just[T](value: T) extends JustOrName[T]

case class JustName[T](name: String) extends JustOrName[T]

case object Unknown extends JustOrName[Nothing]

object JustNone extends JustOrName[Nothing]