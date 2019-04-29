/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The essential persistence type class. it implements both a Show style type class interface, the production of a String representation of the value
  * but also produces an EMon[T] from a String. It Persists and builds objects of type T from CRON syntax. So for example the IntImplicit object in the
  * Persist companion object persists Integers and constructs Integers from Strings. */
trait Persist[T] extends Show[T]
{ 
  def typeStr: String  
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
  def listFromStatementList(l: List[Statement]): List[T] = l.map(fromStatement(_)).collect{ case Good(value) => value }
  
  def findFromStatementList(l: List[Statement]): EMon[T] = listFromStatementList(l) match
  {
    case Nil => FilePosn.emptyError("No values of type found")
    case h :: Nil => Good(h)
    case s3 => bad1(l.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.") 
  }
  
  def settingFromStatement(settingStr: String, st: Statement): EMon[T] = st match
  {
    case MonoStatement(AsignExpr(_, AlphaToken(_, sym), rightExpr), _) if sym == settingStr => fromExpr(rightExpr)
    case _ => bad1(st.startPosn, typeStr -- "not found.")
  }
  
  def settingFromStatementList(list: List[Statement], settingStr: String): EMon[T] = list match
  { case Seq() => FilePosn.emptyError("No Statements")
    case Seq(e1) => settingFromStatement(settingStr, e1)
    case s2 => list.map(settingFromStatement(settingStr, _)).collect{ case g: Good[T] => g } match
    { case Seq(t) => t
      case Nil => bad1(list.startPosn, typeStr -- "not found.")
      case s3 => bad1(list.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.")
    }
  }
}

/** Companion object for the persistence type class. Contains the implicit instances for Scala standard library types. */
object Persist
{
  /** Implicit method for creating List[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def listToPersist[A](implicit ev: Persist[A]): Persist[List[A]] = new PersistListImplicit[A](ev)  
  /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqToPersist[T](implicit ev: Persist[T]): Persist[Seq[T]] = new PersistSeqImplicit[T](ev)
  /** Implicit method for creating Vector[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def vectorToPersist[T](implicit ev: Persist[T]): Persist[Vector[T]] = new PersistVectorImplicit[T](ev)  
  
  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist[A <: AnyRef](implicit ev: Persist[A]): Persist[Array[A]] = new ArrayRefPersist[A](ev) 
  
  implicit def seqToPersistDirect[A](thisSeq: Seq[A])(implicit ev: Persist[A]): PersistSeqDirect[A] = new PersistSeqDirect[A](thisSeq, ev)
  
  implicit def optionToPersist[A](implicit ev: Persist[A]): Persist[Option[A]] = new OptionPersist[A](ev)  
  
  class OptionPersist[A](val ev: Persist[A]) extends Persist[Option[A]]  
  {    
    override def typeStr: String = "Option" + ev.typeStr.enSquare
    override def syntaxDepth: Int = ev.syntaxDepth
    override def show(obj: Option[A]) = obj.fold("")(ev.show(_))
    def showComma(obj: Option[A]): String = show(obj)
    def showSemi(obj: Option[A]): String = show(obj)
    override def showTyped(obj: Option[A]): String = obj.fold("None")(typeStr + ev.show(_).enParenth)
    
    override def fromClauses(clauses: Seq[ostrat.pParse.Clause]): ostrat.EMon[Option[A]] = ???
    override def fromExpr(expr: ostrat.pParse.Expr): ostrat.EMon[Option[A]] = ???
    override def fromStatement(st: ostrat.pParse.Statement): ostrat.EMon[Option[A]] = ???
  }
  
  implicit val NonePersistImplicit: Persist[None.type] = new PersistSimple[None.type]("None")
  {   
    override def show(obj: None.type) = ""   
    def fromExpr(expr: Expr): EMon[None.type] = expr match
    {
      case eet : EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }
  
  implicit val ArrayIntPersistImplicit: Persist[Array[Int]] = new PersistSeqLike[Int, Array[Int]]('Seq, Persist.IntPersist)
  {       
    override def showSemi(thisArray: Array[Int]): String = thisArray.map(ev.showComma(_)).semiFold
    override def showComma(thisArray: Array[Int]): String = thisArray.map(ev.show(_)).commaFold
    override def fromParameterStatements(sts: List[Statement]): EMon[Array[Int]] = bad1(FilePosn.empty, "ArrayInt from statements")
    override def fromClauses(clauses: Seq[Clause]): EMon[Array[Int]] = ???
  
    override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
    { case SemicolonToken(_) => Good(Array[Int]())
      case AlphaBracketExpr(AlphaToken(_, "Seq"), Seq(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) =>
        sts.eMonMap[Int](_.errGet[Int](ev)).map(_.toArray)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }
  
  implicit val IntPersist: Persist[Int] = new PersistSimple[Int]("Int")
  { def show(obj: Int): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Int] = expr match      
    { case IntToken(_, _, i) => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i)
      case  _ => expr.exprParseErr[Int]
    }
  }
  
  implicit val CharPersist: Persist[Char] = new PersistSimple[Char]("Char")
  { def show(obj: Char): String = obj.toString.enqu1
    override def fromExpr(expr: Expr): EMon[Char] = expr match      
    { case CharToken(_, char) => Good(char)        
      case  _ => expr.exprParseErr[Char]
    }
  }
   
  implicit val StringPersist: Persist[String] = new PersistSimple[String]("Str")
  { def show(obj: String): String = obj.enqu
    override def fromExpr(expr: Expr): EMon[String] = expr match      
    { case StringToken(_, stringStr) => Good(stringStr)        
      case  _ => expr.exprParseErr[String]
    }
  }
   
  implicit val LongPersist: Persist[Long] = new PersistSimple[Long]("Long")
  { def show(obj: Long): String = obj.toString
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
   
  implicit val FloatPersist: Persist[Float] = new PersistSimple[Float]("SFloat")
  { def show(obj: Float): String = obj.toString
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
  
  implicit val DoublePersist: Persist[Double] = new PersistSimple[Double]("DFloat")
  { def show(obj: Double): String = obj.toString      
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
   
  implicit val BooleanPersist: Persist[Boolean] = new PersistSimple[Boolean]("Bool")
  { override def show(obj: Boolean): String = obj.toString  
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case AlphaToken(_, str) if str == "true" => Good(true)
      case AlphaToken(_, str) if str == "false" => Good(false)
       case _ => expr.exprParseErr[Boolean]
     }
  }
}
