/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The essential persistence type class. it implements both a Show style type class interface, the production of a String representation of the value
  * but also produces an EMon[T] from a String. It Persists and builds objects of type T from CRON syntax. So for example the IntImplicit object in the
  * Persist companion object persists Integers and constructs Integers from Strings. */
trait Persist[T] extends Show[T] with UnShow[T]

/** Companion object for the persistence type class. The implicit instances for Scala standard library types are to be put in Show. Some methods still
 * in the Persist companion class. */
object Persist
{
  /** Implicit method for creating List[A: Persist] instances. */
  implicit def listPersistImplicit[A](implicit evIn: Persist[A]): Persist[List[A]] = new Persist[List[A]] with ShowIterable[A, List[A]]
  { override val evA: Persist[A] = evIn
    override def fromExpr(expr: Expr): EMon[List[A]] = expr match
    {
      case eet: EmptyExprToken => Good(List[A]())
      case AlphaSquareParenth("Seq", ts, sts) => ??? //sts.eMap(s => evA.fromExpr(s.expr)).toList
      case AlphaParenth("Seq", sts) => ??? // sts.eMap[A](_.errGet[A](evA))
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  implicit def vectorPersistImplicit[A](implicit ev: Persist[A]): Persist[Vector[A]] = new PersistIterable[A, Vector[A]](ev)
  { override def fromExpr(expr: Expr): EMon[Vector[A]] = fromExprLike(expr).map(_.toVector)
   // override def fromParameterStatements(sts: Refs[Statement]): EMon[Vector[A]] = ???
    //override def fromClauses(clauses: Refs[Clause]): EMon[Vector[A]] = ???
  }



  implicit def tuple2Implicit[A1, A2](implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]): Persist[Tuple2[A1, A2]] =
    Persist2[A1, A2, (A1, A2)]("Tuple2", "_1", _._1, "_2", _._2, (a1, a2) => (a1, a2))
}