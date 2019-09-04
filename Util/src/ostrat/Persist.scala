/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The essential persistence type class. it implements both a Show style type class interface, the production of a String representation of the value
  * but also produces an EMon[T] from a String. It Persists and builds objects of type T from CRON syntax. So for example the IntImplicit object in the
  * Persist companion object persists Integers and constructs Integers from Strings. */
trait Persist[T] extends Show[T] with UnShow[T]

/** Companion object for the persistence type class. Contains the implicit instances for Scala standard library types. */
object Persist
{  
  /** Implicit method for creating List[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def listToPersist[A](implicit ev: Persist[A]): Persist[List[A]] = new PersistListImplicit[A](ev)
  /** Implicit method for creating ::[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def consToPersist[A](implicit ev: Persist[A]): Persist[::[A]] = new PersistConsImplicit[A](ev)
  
  implicit def nilToPersist[A](implicit ev: Persist[A]): Persist[Nil.type] = new PersistNilImplicit[A](ev)
  
  /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqToPersist[T](implicit ev: Persist[T]): Persist[Seq[T]] = new PersistSeqImplicit[T](ev)
  /** Implicit method for creating Vector[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def vectorToPersist[T](implicit ev: Persist[T]): Persist[Vector[T]] = new PersistVectorImplicit[T](ev)  
  
  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist[A <: AnyRef](implicit ev: Persist[A]): Persist[Array[A]] = new ArrayRefPersist[A](ev)
  class ArrayRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, Array[A]](ev)
  {
    override def showSemi(thisArray: Array[A]): String = thisArray.map(ev.showComma(_)).semiFold
    override def showComma(thisArray: Array[A]): String = thisArray.map(ev.show(_)).commaFold
    override def fromParameterStatements(sts: Arr[Statement]): EMon[Array[A]] = ???
    override def fromClauses(clauses: Arr[Clause]): EMon[Array[A]] = ???
  
    override def fromExpr(expr: ParseExpr): EMon[Array[A]] =  expr match
    {
      case AlphaBracketExpr(AlphaToken(_, typeName), Seq(ParenthBlock(sts, _, _))) if typeStr == typeName => ???// fromParameterStatements(sts)
      case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName -- "does not equal" -- typeStr)
      case _ => ???// expr.exprParseErr[A](this)
    }
  }

  implicit def someToPersist[A](implicit ev: Persist[A]): Persist[Some[A]] = new Persist[Some[A]]
  {     
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepth: Int = ev.syntaxDepth
    override def show(obj: Some[A]) = ev.show(obj.value)
    override def showSemi(obj: Some[A]) = ev.showSemi(obj.value)
    override def showComma(obj: Some[A]) = ev.showComma(obj.value)
    override def showTyped(obj: Some[A]) =ev.showTyped(obj.value)
    override def fromClauses(clauses: Arr[Clause]): EMon[Some[A]] = ev.fromClauses(clauses).map(Some(_))
    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    {
      case AlphaBracketExpr(AlphaToken(_, "Some"), Arr(ParenthBlock(Arr(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
    override def fromStatements(sts: Arr[Statement]): EMon[Some[A]] = ev.fromStatements(sts).map(Some(_))
  } 
  
  implicit val NonePersistImplicit: Persist[None.type] = new PersistSimple[None.type]("None")
  {   
    override def show(obj: None.type) = ""   
    def fromExpr(expr: Expr): EMon[None.type] = expr match
    {
      case AlphaToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
    
    override def fromStatements(sts: Arr[Statement]): EMon[None.type] = ife(sts.isEmpty, Good(None), bad1(sts.startPosn, "None not found."))
  }
  
  implicit def optionToPersist[A](implicit evA: Persist[A]): Persist[Option[A]] =
    new PersistSum2[Option[A], Some[A], None.type](someToPersist[A](evA), NonePersistImplicit) 
  { override def typeStr: String = "Option" + evA.typeStr.enSquare
    override def syntaxDepth: Int = evA.syntaxDepth
  }
  
  implicit val IntImplicit: Persist[Int] = new PersistSimple[Int]("Int")
  { def show(obj: Int): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Int] = expr match      
    { case IntToken(_, _, i) => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i)
      case  _ => expr.exprParseErr[Int]
    }
  }

  implicit val CharPersist: Persist[Char] = new PersistSimple[Char]("Char")
  { def show(obj: Char): String = obj.toString.enquote1
    override def fromExpr(expr: Expr): EMon[Char] = expr match      
    { case CharToken(_, char) => Good(char)        
      case  _ => expr.exprParseErr[Char]
    }
  }
   
  implicit val StringImplicit: Persist[String] = new PersistSimple[String]("Str")
  { def show(obj: String): String = obj.enquote
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
  
  implicit val DoubleImplicit: Persist[Double] = new PersistSimple[Double]("DFloat")
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
   
  implicit val BooleanImplicit: Persist[Boolean] = new PersistSimple[Boolean]("Bool")
  { override def show(obj: Boolean): String = obj.toString  
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case AlphaToken(_, str) if str == "true" => Good(true)
      case AlphaToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  implicit val ArrayIntImplicit: Persist[Array[Int]] = new PersistSeqLike[Int, Array[Int]](Persist.IntImplicit)
  { override def showSemi(thisArray: Array[Int]): String = thisArray.map(evA.showComma(_)).semiFold
    override def showComma(thisArray: Array[Int]): String = thisArray.map(evA.show(_)).commaFold
    override def fromParameterStatements(sts: Arr[Statement]): EMon[Array[Int]] = bad1(FilePosn.empty, "ArrayInt from statements")
    override def fromClauses(clauses: Arr[Clause]): EMon[Array[Int]] = ???

    override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
    { case SemicolonToken(_) => Good(Array[Int]())
      case AlphaBracketExpr(AlphaToken(_, "Seq"), Arr(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) =>
        sts.eMonMap[Int](_.errGet[Int](evA)).map(_.toArray)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  implicit val ArrIntImplicit: Persist[Arr[Int]] = new PersistSeqLike[Int, Arr[Int]](Persist.IntImplicit)
  { override def showSemi(thisArray: Arr[Int]): String = thisArray.map(evA.showComma(_)).semiFold
    override def showComma(thisArray: Arr[Int]): String = thisArray.map(evA.show(_)).commaFold
    override def fromParameterStatements(sts: Arr[Statement]): EMon[Arr[Int]] = bad1(FilePosn.empty, "ArrayInt from statements")
    override def fromClauses(clauses: Arr[Clause]): EMon[Arr[Int]] = ???

    override def fromExpr(expr: Expr): EMon[Arr[Int]] = expr match
    { case SemicolonToken(_) => Good(Arr[Int]())
      case AlphaBracketExpr(AlphaToken(_, "Seq"), Arr(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) =>
        sts.eMonMap[Int](_.errGet[Int](evA)).map(_.toArr)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  /** Implicit method for creating Arr[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrRefImplicit[A <: AnyRef](implicit ev: Persist[A]): Persist[Arr[A]] = new ArrRefPersist[A](ev)
  class ArrRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, Arr[A]](ev)
  {
    override def showSemi(thisArr: Arr[A]): String = thisArr.map(ev.showComma(_)).semiFold
    override def showComma(thisArr: Arr[A]): String = thisArr.map(ev.show(_)).commaFold
    override def fromParameterStatements(sts: Arr[Statement]): EMon[Arr[A]] = ???
    override def fromClauses(clauses: Arr[Clause]): EMon[Arr[A]] = ???

    override def fromExpr(expr: ParseExpr): EMon[Arr[A]] =  expr match
    {
      case AlphaBracketExpr(AlphaToken(_, typeName), Seq(ParenthBlock(sts, _, _))) if typeStr == typeName => ???// fromParameterStatements(sts)
      case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName -- "does not equal" -- typeStr)
      case _ => ???// expr.exprParseErr[A](this)
    }
  }

  implicit def tuple2Implicit[A1, A2](implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]): Persist[Tuple2[A1, A2]] =
    Persist2[A1, A2, (A1, A2)]("Tuple2", _._1, _._2, (a1, a2) => (a1, a2))
}
