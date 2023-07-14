/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import pWeb._

object TilingPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Tiling Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Tiling Module"), central)
  def central: HtmlDiv = HtmlDiv.classAtt("central", list, centralStr.xCon)
  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Tiling module contains"), coord, tileGeom, grid, sys, proj, terms, turn, exs)

  def coord: HtmlLi = HtmlLi.str("Hex and Square tile coordinate system, allowing the tile centres, the tile sides and the tile vertices to each" +
    " have their own unique 2 inteeger coordinates.")

  def tileGeom: HtmlLi = HtmlLi.str("Tile collections, tile paths and tile polygons and other tile structures, allowing their manipulation as tiles.")

  def grid: HtmlLi = HtmlLi.str("Tile grid classes that describe the tile grids but do not contain any data. For example an 8 by 8 square grid" +
    " would describe a chess board, but the class contains no data as to the position of the chess pieces, or even that it is being used for a" +
    "chess game.")

  def sys: HtmlLi = HtmlLi.str("Tile Grid system classes allowing for multiple hex grids to be manipulated the same as a single grid. This enables" +
    "the code in the EGrid module and other possible non Euclidean tile systems.")

  def proj: HtmlLi = HtmlLi.str("Projections. Projections allow the separation of the visual display of tile geometry from the applications and" +
    " objects within the grid systems.")

  def terms: HtmlLi = HtmlLi.str("Scenario and Game terminology,")

  def turn: HtmlLi = HtmlLi.str("Turn system allowing those grid systems to be used in, multi, simultaneous, homogenious segment simultaneous turn" +
    " games and productions.")

  def exs: HtmlLi = HtmlLi.str("Example games to demonstrate the use of the design principles and code of this module.")

  def centralStr: String ="""
      |    <div>
      |      <h2>Game Terminology</h2>
      |      <ol>
      |        <li><b>ScenWorld</b> The universe of the scenario. Entities within the scenario universe have no knowledge of entities in the GameWorld or the
      |          OutWorld.</li>
      |        <li><b>GameWorld</b> the universe of the players in the game. The assignment of PlayerControl. The time dead lines and time credits for
      |          submission of turn Directives. Rules on credits from InGame achievements or role-playering accreditation on future assignment of InWorld
      |          entities. Rules on Player communication.
      |       </li>
      |        <li><b>OutWorld</b> Anything outside of the Player universe. This can include servers, security, payement etc.</li>
      |        <li><b>Player</b> A GameWorld entity. Maybe a human, an AI or a script.</li>
      |        <li><b>PlayerControl</b> Assignment of an InWorld entity to a Player's control.</li>
      |        <li><b>Directive</b> A player directive given by a player to an InWorld entity at the begining of a turn.</li>
      |        <li><b>ControlHeirarchy</b> The precedence order of PlayerControl for Directives if multiple players are given control of an InGame
      |          entity.</li>
      |        <li><b>Intention</b> Intention to make an effect by an InGame object. An Intention has a single SourceTile and a single TargetTile.</li>
      |        <li><b>SourceTile</b> The location of an entity making an Intention.</li>
      |        <li><b>TargetTile</b> The target an Intention. Maybe the sourceTile. For some Intentions it must be an adjacent tile for others it maybe a non
      |          adjacent tile.</li>
      |      </ol>
      |    </div>
      |
      |    <div>
      |      <h2>Turn Resolution</h2>
      |      <ol>
      |        <li>Verify directives from Players are valid and under the PlayerControl</li>
      |        <li>Resolve ControlHeirarchy</li>
      |        <li>Map Directives to intentions. In more complex games some Directives maybe ignored or defered by InGame Objects.</li>
      |        <li>Aggregate Intentions.</li>
      |        <li>Add subsidary Intentions.</li>
      |        <li>Resolve Segment
      |          <ul>
      |            <li>Accumulate. Aggregate the intentions in their TargetTiles.</li>
      |            <li>Ajudicate. Determine the outcome of the Intentions and processes.</li>
      |            <li>Consolidate. Create a new game state from the outcomes of the Intentions on the TargetTile. No Movement is implemented during this
      |              stage.</li>
      |            <li>Distribute. Distribute the movement effects of the resolution of the TargetTiles on the SourceTiles in the Consilidated Game State.
      |              Moving entities from SourceTiles to TargetTiles when appropriate.</li>
      |          </ul>
      |        </li>
      |        <li>Repeat Resolve Segement till the given number of segments has been repeated.</li>
      |        <li>Inform the players, human and AI, of the Turn resolution.</li>
      |      </ol>
      |    </div>
      |""".stripMargin
}