public class review4 {
    <?php
    namespace App\Http\Controllers;
    use App\Building;
    use Illuminate\Http\Request;
    use App\Level;
    use App\Area;
    use App\Table;
    use DB;
    class BuildingController extends Controller
    {
        //gets all stored buildings
        public function getBuildings(){
            return Building::all();
        }
        //gets all the levels inside a building when providing the building's ID
        public function showLevels($id){
            $building_id=$id;
            if(!Building::find($id)){
                return [];
            }
            $levels=Building::find($building_id)->levels()->get();
            return $levels;
        }
1
        //Deletes all the tables, areas, and levels inside a building then deletes the building itself from the DB
        public function delete(Request $request,$id){
            $req = $request->all();
            if (!Building::find($id)){
                $req['id']=$id;
                return ['status'=>true,'response' => "Building not found!","request"=>$req];
            }
            $levels=Building::find($id)->levels();
//Deleting all the levels inside the building
            foreach ($levels->get() as $level){
                $level_id=$level->id;
                $areas=Level::find($level_id)->areas();
// Deleting all the areas inside the building's levels
                foreach ($areas->get() as $area){
                    $area_id=$area->id;
                    $tables=Area::find($area_id)->tables();
                    $tables->delete();
                }
                $areas->delete();
            }
            $levels->delete();
            DB::table('buildings')->where('id', '=', $id)->delete();
            return ['status'=>true,'response' => "Building Deleted!","request"=>$req];
        }
        //adds a building to the DB
        public function store(Request $request)
        {
            $building=new Building;
            if(isset($request->name)){
                $building->name=$request->name;
            }else{
                $building->name='building';
            }
            $building->save();
            return ['status'=>true,'response' => "Building Created!","data"=>$request->all()];
            2
        }
        //changes a building's name in the DB
        public function update(Request $request,$id){
            DB::table('buildings')
->where('id', $id)
->update(['name' =>$request->name]);
            return ['status'=>true,'response' => "Building Edited!","data"=>$request->all()];
        }
    }
}
