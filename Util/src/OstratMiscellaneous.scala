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
{
  def str: String = arrayUnsafe.foldLeft("")(_ + "/" + _)

  override def toString: String = "DirPathAbs" + str.enParenth
}

object DirPathAbs
{
  implicit val persistImplicit: Persist[DirPathAbs] = new Persist[DirPathAbs]
  {  override def typeStr: String = "DirnPathAbs"
    /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
     * rather than a method on the object being shown. */
    override def strT(obj: DirPathAbs): String = ???

    /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
     * should always be determined at compile time or if sometimes it should be determined at runtime. */
    override def syntaxDepthT(obj: DirPathAbs): Int = ???

    override def showDecT(obj: DirPathAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

    /** Tries to return a value of the type from an RSON expression [[Expr]] that has been parsed from a String or text file. This method must be
     * implemented by all instances. */
    override def fromExpr(expr: Expr): EMon[DirPathAbs] = expr match
    { case SlashToken(_) => Good(new DirPathAbs(Array[String]()))
      case PathToken(_, array) => Good(new DirPathAbs(array))
      case expr => expr.startPosn.bad("Not an absolute path")
    }
  }
}