/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** Common super trait for [[TellDec]], [[Show]] and [[Unshow]]. All of which inherit the typeStr property. */
trait Persist extends Any
{ /** The type of the object to be persisted. */
  def typeStr: String

  /** This property determines if the type can be used with [[Multiple]] syntax. {{{MyObj * 7}}}. for describing sequences succinctly. This is not
   * desirable for some types such as numerical and mathematical vector types as this could be confusing 3 * 4 should resolve to an [[Int]] of value
   * 12, not a Multiple(3, 4). */
  def useMultiple: Boolean = true
}

/** Type class inatances for both [[Show]] and [[Unshow]]. Only use this class where all possilbe requirements have PersistBoth instances. Do not use
 * it for such types as Sequences where all the potential components are not known. */
trait PersistBoth[A] extends Show[A] with Unshow[A]

object PersistBoth
{
  /** Implicit [[Show]] and [[Unshow]] instances / evidence for [[Double]]. */
  val doubleEv: PersistBoth[Double] = new PersistBoth[Double]
  { override def typeStr: String = "DFloat"
    override def syntaxDepth(obj: Double): Int = 1
    override val useMultiple = false

    def strT(obj: Double): String =
    { val s1 = obj.toString
      ife(s1.last == '0', s1.dropRight(2), s1)
    }

    override def show(obj: Double, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = -1): String =
    { val orig = obj.toString
      val len = orig.length
      val minPlacesAdj: Int = ife(maxPlaces < 0, minPlaces, minPlaces.min(maxPlaces))
      val dotIndex: Int = orig.indexOf('.')
      val fracLen: Int = len - dotIndex - 1

      val inner: String = None match
      { case _ if maxPlaces < 0 & minPlacesAdj <= 1 & orig.last == '0' => orig.dropRight(2)
        case _ if fracLen < minPlacesAdj => orig + (minPlacesAdj - fracLen).repeatChar('0')
        case _ if maxPlaces < 0 => orig
        case _ if maxPlaces == 0 => orig.dropRight(fracLen + 1)
        case _ if fracLen > maxPlaces => orig.dropRight(fracLen - maxPlaces)
        case _ => orig
      }

      style match
      { case ShowTyped => typeStr + inner.enParenth
        case _ => inner
      }
    }

    override def fromExpr(expr: Expr): EMon[Double] = expr match
    { case ValidFracToken(d) => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "+" => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "-" => Good(-d)
      case _ => expr.exprParseErr[Double]
    }
  }

  val boolEv: Show[Boolean] = new PersistBothSimple[Boolean]("Bool")
  {
    /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
     * rather than a method on the object being shown. */
    override def strT(obj: Boolean): String = ???


    /** Tries to return a value of the type from an RSON expression [[Expr]] that has been parsed from a String or text file. This method must be
     * implemented by all instances. */
    override def fromExpr(expr: Expr): EMon[Boolean] = ???
  }
}

abstract class PersistBothSimple[A](val typeStr: String) extends PersistBoth[A]
{
  final override def syntaxDepth(obj: A): Int = 1

  final override def show(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = way match {
    case ShowTyped => typeStr + strT(obj).enParenth
    case ShowUnderScore => "_"
    case _ => strT(obj)
  }
}