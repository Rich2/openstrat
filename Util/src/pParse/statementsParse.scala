/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object to parse a sequence of Statement members into a  sequence of Statements. Statement members are either nonBracketTokens
 *  or parsed BracketBlocks. Parses String (with RSON syntax) searching for the String terminator. Returns error if end of file found first. */
object statementsParse
{
  /** Parses a sequence of block members raw Statement where bracket blocks have already been parsed into a sequence of Statements. */
  def apply(implicit inp: Arr[BlockMem]): ERefs[Statement] =
  {
    val acc: Buff[Statement] = Buff()
    var subAcc: Buff[StatementMem] = Buff()

    def loop(rem: ArrOff[BlockMem]): ERefs[Statement] = rem match
    { case ArrOff0() if subAcc.isEmpty => Good(acc.toArr)
      case ArrOff0() => statementParse(subAcc.toArr, NoRef).map(acc :+ _).map(_.toArr)
      case ArrOff1Tail(st: SemicolonToken, tail) if subAcc.isEmpty => { acc.append(EmptyStatement(st)); loop(tail) }

      case ArrOff1Tail(st: SemicolonToken, tail) => statementParse(subAcc.toArr, OptRef(st)).flatMap{ g =>
          acc.append(g)
          subAcc = Buff()
          loop(tail)
        }

      case ArrOff1Tail(sm: StatementMem, tail) => { subAcc.append(sm); loop(tail) }
      case u => excep("Statement Loop, impossible case")
    }

    loop(inp.offset0)
  }
}