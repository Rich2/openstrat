/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, reflect.ClassTag

/** A convenience trait type class trait for persistence, that combines the [[Show]] and [[Unshow]] type classes. Most if not all final classes that
 * inherit from this trait will require type class instances of ShowT and UnShowT to implement [[Persist]]'s members. It is most important that these
 * implicit parameter instances be specified as separate ShowT and UnShowT parameters. Do not combine them into a Persist parameter. There are no
 * implicit instances for [[Int]], [[Double]], [[List]] etc in the [[Persist]] companion object, the Persist components for these standard types will
 * be found in the ShowT and UnShow companion objects. */
trait Persist[T] extends Show[T] with Unshow[T]

/** The base trait for the persistence of algebraic product types, including case classes. Note the arity of the product, its size is based on the
 *  number of logical parameters. For example, a LineSeg is a product 2, it has a start point and an end point, although its is stored as 4 parameters
 *  xStart, yStart, xEnd, yEnd. */
trait PersistN[R] extends Persist[R] with ShowN[R]

/** [[Persist]] trait for types whose objects inherit from [[ShowDecN]]. */
trait PersistShowN[R <: TellN] extends PersistN[R] with ShowNeding[R]

/** A Persist class described by a single value. This may be removed. Its not clear whether this means a single token or not. */
abstract class PersistSimple[A](val typeStr: String) extends ShowSimpleing[A] with Persist[A]

/** I think this class may be redundant and can be replace by a more general PersistSum class for displaying algebraic sum types. */
abstract class PersistSingletons[A <: ShowSimpled](typeStr: String) extends PersistSimple[A](typeStr)
{
  def singletons: RArr[A]

  @inline override def strT(obj: A): String = obj.str

  def fromExpr(expr: Expr): EMon[A] = expr match
  { case IdentifierToken(str) => singletons.find(el => el.str == str).toEMon1(expr, typeStr -- "not parsed from this Expression")
    case e => bad1(e, typeStr -- "not parsed from this Expression")
  }
}

object PersistSingletons
{
  def apply[A <: ShowSimpled](typeStr: String, singletonsIn: RArr[A]): PersistSingletons[A] = new PersistSingletons[A](typeStr)
  { override def singletons: RArr[A] = singletonsIn
  }

  def apply[A <: ShowSimpled](typeStr: String, singletonsIn: A*)(implicit ct: ClassTag[A]): PersistSingletons[A] = new PersistSingletons[A](typeStr)
  {  override def singletons: RArr[A] = singletonsIn.toArr
  }
}