/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv

object MenuSeq
{  def apply(nodes: MenuNode*): MenuSeq = Seq(nodes:_ *)
}

object MenuBranch
{ def apply(text: String): MenuBranch = new MenuBranch(text, Nil)
  def apply(text: String, nodes: MenuSeq) = new MenuBranch(text, nodes)
}

class MenuBranch(text: String, val nodes: Seq[MenuNode]) extends MenuSub(text) 
{ def addNode(operand: MenuNode): MenuBranch = new MenuBranch(text, nodes :+ operand)
  def addNodes(operands: MenuNode*): MenuBranch = new MenuBranch(text, operands.toList ++ nodes)
}

object MenuBranchDynamic
{ def apply(text: String, getSubMenu: () => Seq[MenuNode]): MenuBranchDynamic = new MenuBranchDynamic(text, getSubMenu)
}

class MenuBranchDynamic(text: String, val getSubMenu: () => Seq[MenuNode]) extends MenuSub(text)

object MenuLeaf
{ def apply(text: String): MenuLeaf = new MenuLeaf(text, () => {})
  def apply(text: String, params: () => Unit *): MenuLeaf = new MenuLeaf(text, params.fold(() => {})(_ + _))
}

class MenuLeaf(text: String, val action: () => Unit) extends MenuNode(text)
{	def doAfter(action: () => Unit): MenuLeaf = MenuLeaf(text, action, action)
	def +(operand: () => Unit): MenuLeaf = MenuLeaf(text, action + operand)
}

object MenuSub
{
  implicit class MenuSubSeqImp(s: Seq[MenuSub])
  { def subFoldEach(fBranch: MenuBranch => Unit, fDynamic: MenuBranchDynamic => Unit): Unit = s.foreach(i => i.subFold(fBranch, fDynamic))
    def merge(others: Seq[MenuSub] *): Seq[MenuSub] = MenuSub.merge((others :+ s).flatten)
  }
   
  def merge(seq: Seq[MenuSub]): Seq[MenuSub] =
  { /** Checks for Menu Items with the same heading.*/
    val v1: Map[String, Seq[MenuSub]] = seq.groupBy(_.text.toLowerCase)
    val v2: Seq[Seq[MenuSub]] = v1.values.toSeq

  v2.map(i => i match
  { case Seq(l) => l //There is only Menu Node for this heading

    case s => if (s.exists(j => j.isInstanceOf[MenuBranchDynamic]))
        MenuBranchDynamic(s.head.text, () => s.flatMap(_.subFold(_.nodes, _.getSubMenu())))
      else MenuBranch(s.head.text, MenuNode.merge(s.asInstanceOf[Seq[MenuBranch]].flatMap(_.nodes)))
    })
  }
}

abstract sealed class MenuSub(text: String) extends MenuNode(text)
{
  def subFold[T](fBranch: MenuBranch => T, fDynamic: MenuBranchDynamic => T): T = this match
  { case m: MenuBranch => fBranch(m)
	  case m: MenuBranchDynamic => fDynamic(m)
  }
}

object MenuNode
{
  implicit class MenuNodeSeqImp(s: Seq[MenuNode])
  {
    def foldEach(fLeaf: MenuLeaf => Unit, fBranch: MenuBranch => Unit, fDynamic: MenuBranchDynamic => Unit): Unit =
         s.foreach(i => i.fold(fLeaf, fBranch, fDynamic))         
    def merge(others: Seq[MenuNode] *): Seq[MenuNode] = MenuNode.merge((others :+ s).flatten)
  }
   
  def merge(seq: Seq[MenuNode]): Seq[MenuNode] =
  {
    val v1: Map[String, Seq[MenuNode]] = seq.groupBy(_.text.toLowerCase)
    val v2: Seq[Seq[MenuNode]] = v1.values.toSeq
    v2.map {
      case Seq(l) => l
      
      case s if s.exists(i => i.isInstanceOf[MenuBranchDynamic]) => MenuBranchDynamic(s.head.text, () => s.flatMap(_.fold(
           l => Seq(MenuLeaf("Duplicate menu entry", l.action)),
             _.nodes,
             _.getSubMenu()
             )))
        
      case s =>
      {
        val leaves = s.collect{case l : MenuLeaf => MenuLeaf("Duplicate menu entry", l.action)}
        val branches = merge(s.collect{case b: MenuBranch => b.nodes }.flatten)
        MenuBranch(s.head.text, leaves ++ branches)
      }      
    }
  }
}

abstract sealed class MenuNode(val text: String)
{ def toTree: Seq[MenuNode] = List(this)   
  def +(other: MenuNode): MenuSeq = MenuSeq(this, other)
  def ifPlus(b: Boolean, other: MenuNode) = if (b) MenuSeq(this, other) else MenuSeq(this)
 
  def fold[T](fLeaf: MenuLeaf => T, fBranch: MenuBranch => T, fDynamic: MenuBranchDynamic => T): T = this match
  { case m: MenuLeaf => fLeaf(m)
	  case m: MenuBranch => fBranch(m)
	  case m: MenuBranchDynamic => fDynamic(m)
  }
}