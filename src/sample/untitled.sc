/*(-1).to(1).toArray.flatMap( x=>((-1).to(1).toArray).map((x,_))).filter(x=>(Math.abs(x._1) ^Math.abs(x._2) )!=0)
class Point {
  private var _x = 0
  private var _y = 0
  private val bound = 100
  private var  _list=List[(Int)]()
  def x = _x
  def x_= (newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printWarning
  }

  def y = _y
  def y_= (newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printWarning
  }
  def list=_list
  def list_=(l: List[Int]){_list=l}
  private def printWarning = println("WARNING: Out of bounds")
}

val point1 = new Point
point1.x = 99
point1.y = 101
point1.list match {case List() => println(1)}
point1.list=List(5,9)
point1.list

 */
import org.opencv.core.Size
import sample.Figure
object figureInfo {
  var color = 0;

  def getInfo(array: Array[Byte], size: Size) = {
    val mat = array.grouped(size.width.toInt).toArray
    for (y <- 0 until size.height.toInt; x <- 0 until size.width.toInt){
      if(mat(y)(x)== -1)
      {
        color+=1
        getFigure(mat,(y,x))
      }
    }
    mat.flatten
  }

  private def getFigure(mat: Array[Array[Byte]], coordinate: (Int, Int), figure: Figure): Figure = {
    if (figure.list.isEmpty)
      figure.color = color
    mat(coordinate._1)(coordinate._2) = color.toByte;
    figure.add(coordinate)
    val deltas = (-1).to(1).toArray.flatMap(x => ((-1).to(1).toArray).map((x, _)))
    for (delta <- deltas) {

      val b = mat(coordinate._1 + delta._1)(coordinate._2 + delta._2)
      if (b == -1) {
      //  println(coordinate)
        mat(coordinate._1 + delta._1)(coordinate._2 + delta._2) = figure.color.toByte
        figure.add(getFigure(mat, (coordinate._1 + delta._1, coordinate._2 + delta._2), figure.add(coordinate)).list)
      }
    }


    figure
  }
  private def getFigure(mat: Array[Array[Byte]], coordinate: (Int, Int)): Unit = {
    mat(coordinate._1)(coordinate._2) = color.toByte;
    val deltas = (-1).to(1).toArray.flatMap(x => ((-1).to(1).toArray).map((x, _)))
    for (delta <- deltas) {

      val b = mat(coordinate._1 + delta._1)(coordinate._2 + delta._2)
      if (b == -1) {
        println(coordinate)
        mat(coordinate._1 + delta._1)(coordinate._2 + delta._2) = (color *5).toByte
        getFigure(mat, (coordinate._1 + delta._1, coordinate._2 + delta._2))
      }
    }
  }

}

val a=Array[Byte]( 0,0,0,0,0,0,0,0,0,0,0,
  0,0,0,0,0,0,0,0,0,0,0,
  0,0,0,0,-1,-1,-1,0,0,0,0,
  0,0,0,-1,-1,-1,-1,0,0,0,0,
  0,0,0,-1,-1,-1,-1,0,-1,0,0,
  0,0,0,0,0,0,0,0,-1,-1,0,
  0,0,0,0,0,0,0,0,0,0,0)
figureInfo.getInfo( a,new Size(11,7)).grouped(11).toArray