/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** Persistence base trait for PersistCase and PerististSeqLike. Some methods probably need to be moved down into sub classes. */
abstract class PersistCompound[R](typeSym: Symbol) extends Persist[R](typeSym)
{ final override def persist(obj: R): String = typeStr + persistSemi(obj).enParenth 
  @inline override def persistTyped(obj: R): String = persist(obj)

  override def fromExpr(expr: ParseExpr): EMon[R] =  expr match
  {
    case AlphaBracketExpr(AlphaToken(_, typeName), Seq(ParenthBlock(sts, _, _))) if typeSym == typeName => fromParameterStatements(sts)
    case AlphaBracketExpr(AlphaToken(fp, typeName), _) => bad1(fp, typeName.name -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }  
  def fromParameterStatements(sts: Seq[Statement]): EMon[R]
  override def fromStatement(st: Statement): EMon[R] = st match
  { case MonoStatement(expr, _) => fromExpr(expr)
    case ClausedStatement(cls, _) => bad1(cls.head.startPosn, "Claused Statement")
    case es @ EmptyStatement(st) => es.asError
  } 
//      def multiLine(rem: Seq[Persist[_]], acc: Seq[String]): Seq[String] = rem.fHead(
//            Seq(persistName) ++ acc,
//            (h, tail) => h.persistComma match
//            {
//               case Seq(h) => contLine(rem, acc, h.ind2 - ";")
//               case s => multiLine(tail, acc ++ s.map(_.ind2 - ";"))
//            }
//         )
//      
//      persistMems match
//      {
//         case Seq() => Seq(persistName + "()")
//         case mems if mems.forall(_.persistComma.length == 1) && (mems.map(_.persistComma.head.length).sum <= maxLen) => mems.last.persistComma.head match
//         {
//            case "" =>
//               {
//                  val persistedMems: Seq[String] = mems.map(_.persistComma.head - ";")
//                  val persistedMemsStr: String = persistedMems.strFold(" ")
//                  Seq(persistName - persistedMemsStr.enParenth)
//               }
//            case lastStr =>
//               {
//                  val persistedMems: Seq[String] = mems.init.map(_.persistComma.head - ";").:+(lastStr)
//                  val persistedMemsStr: String = persistedMems.strFold(" ")
//                  Seq(persistName - persistedMemsStr.enParenth) 
//               }
//         }
//         case Seq(mem1, tail @ _*) => mem1.persistComma match
//         {            
//            case Seq(line1) => contLine(tail, Nil, line1 - ";")
//            case mem1Lines => multiLine(tail, mem1Lines.init.map(_.ind2).:+(mem1Lines.last.ind2 - ";"))
//         }
//      }
//   }
//   def persistStatements: String = "#" - persistName - persistMems.foldLeft("")(_.nl - _.persistFull.strFold("\n")) 
}

abstract class PersistSeqLike[A, R](typeSym: Symbol, val ev: Persist[A]) extends PersistCompound[R](typeSym)
{
  override def typeStr = "Seq" + ev.typeStr.enSquare
  override def syntaxDepth = ev.syntaxDepth + 1    
}


