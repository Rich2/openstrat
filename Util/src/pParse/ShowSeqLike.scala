package ostrat
package pParse

trait ShowSeqLike[A, R] extends ShowCompound[R]
{
  def evA: Show[A]
  override def typeStr = "Seq" + evA.typeStr.enSquare
  override def syntaxDepth = evA.syntaxDepth + 1

}
