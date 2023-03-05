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
  implicit val persistImplicit: Persist[DirPathAbs] = new Persist[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"
    override def strT(obj: DirPathAbs): String = obj.str
    override def syntaxDepthT(obj: DirPathAbs): Int = 1
    override def showDecT(obj: DirPathAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
      case ShowTyped | ShowStdTypedFields => typeStr + obj.str.enParenth
      case _ => obj.str
    }

    override def fromExpr(expr: Expr): EMon[DirPathAbs] = expr match
    { case SlashToken(_) => Good(new DirPathAbs(Array[String]()))
      case PathToken(_, array) => Good(new DirPathAbs(array))
      case expr => expr.startPosn.bad("Not an absolute path")
    }
  }
}