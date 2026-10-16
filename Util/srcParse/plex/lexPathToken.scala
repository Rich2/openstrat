/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex

object lexPathToken
{
  def apply(remOff: CharsOff, tpStart: TextPosn)(implicit charArr: CharArr): LexarExcEither[(CharsOff, TextPosn, Token)] =
  { var buff = StringBuff ()
   def loop(remOff: CharsOff, tp: TextPosn): LexarExcEither[(CharsOff, TextPosn, Token)] = remOff match
   {
     case CharsOffHead2('/', LetterOrUnderscoreChar(_)) => lexIdentifierToken(remOff.drop1, tp.right1).flatMap{(co, tp, token) =>
       buff.grow(token.srcStr)
       loop(co, tp)
     }
     case _ => Right(remOff, tp, PathToken(tpStart, buff.toArray))
   }
   loop(remOff, tpStart)
  }
}