/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The essential persistence type class. it implements both a Show style type class interface, the production of a String representation of the value
  * but also produces an EMon[T] from a String. It Persists and builds objects of type T from CRON syntax. So for example the IntImplicit object in the
  * Persist companion object persists Integers and constructs Integers from Strings. */
abstract class Persist[T](val typeSym: Symbol)
{ def syntaxDepth: Int
  def typeStr: String = typeSym.name

  /** Provides the standard string representation for the object */
  def persist(obj: T): String
  
  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 2. if it is 2 or greater return the full typed data. */
  def persistComma(obj: T): String
  
  /** Return the defining member values of the type as a series of semicolon seperated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  def persistSemi(obj: T): String

  /** For most objects persistTyped wil return the same value as persist(obj: T), for PeristValues the value will be type enclosed. 4.persistTyped
    * will return Int(4) */
  def persistTyped(obj: T): String

  def fromExpr(expr: Expr): EMon[T]
  def fromClauses(clauses: Seq[Clause]): EMon[T]
  def fromClauses2[A1, A2, B](f: (A1, A2) => B, clauses: Seq[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2]): EMon[B] = clauses match
  { case Seq(c1, c2) => ev1.fromExpr(c1.expr).map2(ev2.fromExpr(c2.expr), f)
    case _ => excep("from clauses exception")
  }
   
  def fromClauses3[A1, A2, A3, B](f: (A1, A2, A3) => B, clauses: Seq[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): EMon[B] =
    clauses match
  { case Seq(c1, c2, c3) => ev1.fromExpr(c1.expr).map3(ev2.fromExpr(c2.expr), ev3.fromExpr(c3.expr), f)
  }
  
  def fromClauses4[A1, A2, A3, A4, B](f: (A1, A2, A3, A4) => B, clauses: Seq[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2],
      ev3: Persist[A3], ev4: Persist[A4]): EMon[B] = clauses match
  { case Seq(c1, c2, c3, c4) => ev1.fromExpr(c1.expr).map4(ev2.fromExpr(c2.expr), ev3.fromExpr(c3.expr), ev4.fromExpr(c4.expr), f)
  }
  
  /** Trys to build an object of type T from the statement */
  def fromStatement(st: Statement): EMon[T]
  
//  /** Tries to find first statement that can build an object of type T and returns object */
//  def fromStatementList(list: List[Statement]): EMon[T] = list match
//  { case Seq() => FilePosn.emptyError("No Statements")
//    case Seq(e1) => fromStatement(e1)
//    case s2 => list.map(fromStatement(_)).collect{ case g: Good[T] => g } match
//    { case Seq(t) => t
//      case Seq() => bad1(list.startPosn, typeStr -- "not found.")
//      case s3 => bad1(list.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.")
//    }
//  }
   
  def listFromStatementList(l: List[Statement]): List[T] =
  {
    deb(l.length.toString)
    val r1 = l.map(fromStatement(_))
    deb(r1.toString)
    val r2 = r1.collect{ case Right(value) => value }
    deb(r2.length.toString)
    r2
  }
  def findFromStatementList(l: List[Statement]): EMon[T] = listFromStatementList(l) match
  {
    case Nil => FilePosn.emptyError("No values of type found")
    case h :: Nil => Good(h)
    case s3 => bad1(l.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.") 
  }
  
  def settingFromStatement(settingSym: Symbol, st: Statement): EMon[T] = st match
  {
    case MonoStatement(AsignExpr(_, AlphaToken(_, sym), rightExpr), _) if sym == settingSym => fromExpr(rightExpr)
    case _ => bad1(st.startPosn, typeStr -- "not found.")
  }
  
  def settingFromStatementList(list: List[Statement], settingSym: Symbol): EMon[T] = list match
  { case Seq() => FilePosn.emptyError("No Statements")
    case Seq(e1) => settingFromStatement(settingSym, e1)
    case s2 => list.map(settingFromStatement(settingSym, _)).collect{ case g: Good[T] => g } match
    { case Seq(t) => t
      case Nil => bad1(list.startPosn, typeStr -- "not found.")
      case s3 => bad1(list.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.")
    }
  }
}

/** Companion object for the persistence type class. Contains the implicit instances for Scala standard library types. */
object Persist
{
  /** Implicit method for creating List[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def listToPersist [T](implicit ev: Persist[T]): Persist[List[T]] = new PersistListImplicit[T](ev)  
  /** Implicit method for creating Seq[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqToPersist [T](implicit ev: Persist[T]): Persist[Seq[T]] = new PersistSeqImplicit[T](ev)
  /** Implicit method for creating Vector[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def vectorToPersist [T](implicit ev: Persist[T]): Persist[Vector[T]] = new PersistVectorImplicit[T](ev)  
  
  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist [A <: AnyRef](implicit ev: Persist[A]): Persist[Array[A]] = new ArrayRefPersist[A](ev)  
  
  implicit object ArrayIntToPersist extends PersistSeqLike[Int, Array[Int]]('Seq, Persist.IntPersistImplicit)
  {       
  override def persistSemi(thisArray: Array[Int]): String = thisArray.map(ev.persistComma(_)).semicolonFold
  override def persistComma(thisArray: Array[Int]): String = thisArray.map(ev.persist(_)).commaFold
  override def fromParameterStatements(sts: List[Statement]): EMon[Array[Int]] = bad1(FilePosn.empty, "ArrayInt from statements")
  override def fromClauses(clauses: Seq[Clause]): EMon[Array[Int]] = ???
  
  override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
  { case SemicolonToken(_) => Good(Array[Int]())
//         //For Some reason the compile is not finding the implicit
    case AlphaBracketExpr(AlphaToken(_, 'Seq), Seq(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) =>
      sts.eMonMap[Int](_.errGet[Int](ev)).map(_.toArray)
    case e => bad1(expr, "Unknown Exoression for Seq")
  }
  }
  
  implicit object IntPersistImplicit extends PersistSimple[Int]('Int)
  { def persist(obj: Int): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Int] = expr match      
    { case IntToken(_, _, i) => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i)
      case  _ => expr.exprParseErr[Int]
    }
  }
   
  implicit object StringPersist extends PersistSimple[String]('Str)
  { def persist(obj: String): String = obj.enqu
    override def fromExpr(expr: Expr): EMon[String] = expr match      
    { case StringToken(_, stringStr) => Good(stringStr)        
      case  _ => expr.exprParseErr[String]
    }
  }
  //   override def persistStr: String = thisString.foldLeft("")((accStr, head) => head match
//         {
//      case '\"' | '\'' | '\\' => accStr :+ '\\' :+ head
//      case '\n' =>  accStr :+ '\\' :+ 'n'
//      case '\b' => accStr :+ '\\' :+ 'b'
//      case '\t' => accStr :+ '\\' :+ 't'
//      case '\f' => accStr :+ '\\' :+ 'f'
//      case '\r' => accStr :+ '\\' :+ 'r'
//      case c => accStr :+ c
//         }).enqu
   
  implicit object LongPersist extends PersistSimple[Long]('Long)
  { def persist(obj: Long): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Long] = expr match      
    { case IntToken(_, _, i) => Good(i.toLong)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toLong)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i.toLong)
      case LongIntToken(_, _, li) => Good(li)
      case PreOpExpr(op, LongIntToken(_, _, li)) if op.str == "+" => Good(li)
      case PreOpExpr(op, LongIntToken(_, _, li)) if op.str == "-" => Good(-li)
      case  _ => expr.exprParseErr[Long]
    }
  }   
   
  implicit object FloatPersist extends PersistSimple[Float]('SFloat)
  { def persist(obj: Float): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Float] = expr match      
    { case IntToken(_, _, i) => Good(i.toFloat)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toFloat)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-(i.toFloat))
      case FloatToken(_, _, d) => Good(d.toFloat)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "+" => Good(d.toFloat)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "-" => Good(-d.toFloat)
      case  _ => expr.exprParseErr[Float]
    }
  }
  
  implicit object DoublePersist extends PersistSimple[Double]('DFloat)
  { def persist(obj: Double): String = obj.toString      
    override def fromExpr(expr: Expr): EMon[Double] = expr match      
    { case IntToken(_, _, i) => Good(i.toDouble)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toDouble)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-(i.toDouble))
      case FloatToken(_, _, d) => Good(d)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "+" => Good(d)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "-" => Good(-d)
      case  _ => expr.exprParseErr[Double]
    } 
  }   
   
  implicit object BooleanPersist extends PersistSimple[Boolean]('Bool)
  { override def persist(obj: Boolean): String = obj.toString  
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case AlphaToken(_, str) if str == 'true => Good(true)
      case AlphaToken(_, str) if str == 'false => Good(false)
       case _ => expr.exprParseErr[Boolean]
     }
  }
}
