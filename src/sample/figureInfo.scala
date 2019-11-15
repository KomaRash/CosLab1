package sample
import org.opencv.core.Size
import sample.Kmeans.Point
object figureInfo {
  var color = 0;
  def kmeans(array: Array[Byte], size: Size): Array[Byte]={
    val figures=getInfo(array,size)
    val  points=figures.map(f=> (new  Point(f.vectorKmeans.square,f.vectorKmeans.perimetr,f.vectorKmeans.elongation,f.vectorKmeans.comp)))
    val clusters=Kmeans.buildClusters(points,Kmeans.createRandomCentroids(points))
    var mat = array.grouped(size.width.toInt).toArray
    clusters.zipWithIndex.foreach({
      case ((centroid, members),index) =>
        members.foreach({ member => println(s"Centroid: $centroid Member: $member") })
        index match {
          case 0 => for (member <- members; figure <-figures.filter(x=>Point(x.vectorKmeans.square,x.vectorKmeans.perimetr,x.vectorKmeans.elongation,x.vectorKmeans.comp)
            .equals(member));coord <- figure.list) {
            mat(coord._1)(coord._2) = -1
          }
          case 1 => for (member <- members; figure <-figures.filter(x=>x.equals(member));coord <- figure.list) {
        mat(coord._1)(coord._2) = 125
        }
    }
    }
    )
    mat.flatten


  }
  def getInfo(array: Array[Byte], size: Size) = {
    val mat = array.grouped(size.width.toInt).toArray
    val deltas = (-1).to(1).toArray.flatMap(x => ((-1).to(1).toArray).map((x, _)))
    for (y <- 0 until size.height.toInt; x <- 0 until size.width.toInt){
     if(mat(y)(x)== -1)
       {
          if(!deltas.map(xy => (xy, mat(y + xy._1)(x + xy._2))).exists(_._2 > 0)) {
            color += 1
            getFigure(mat, color,(y, x), 0)
          }
          else
            getFigure(mat,deltas.map(xy => (xy, mat(y + xy._1)(x + xy._2))).filter(_._2>0).head._2 ,(y, x), 0)
         }
    }
    ListFigure.findFigure(mat,size)
  }

  private def getFigure(mat: Array[Array[Byte]],color:Int, coordinate: (Int, Int),acc:Int) :Unit= {
    try {
      mat(coordinate._1)(coordinate._2) = color.toByte;
      val deltas = (-1).to(1).toArray.flatMap(x => ((-1).to(1).toArray).map((x, _)))
      for (delta <- deltas) {

        val b = mat(coordinate._1 + delta._1)(coordinate._2 + delta._2)
        if (b == -1 && acc<100) {

          mat(coordinate._1 + delta._1)(coordinate._2 + delta._2) = (color).toByte
          getFigure(mat,color, (coordinate._1 + delta._1, coordinate._2 + delta._2),acc+1 )
        }
      }
    }
  catch
    {
      case x:java.lang.StackOverflowError=>
         x.printStackTrace()
    }

  }
}
