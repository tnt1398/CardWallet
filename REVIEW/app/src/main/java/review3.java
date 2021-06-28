import android.view.MenuItem;
import android.widget.Toast;
import com.example.review.R;

public class review3 {

    //Button click handling
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.actionSaveNote:
                String newTitle = title.getText().toString();
                String newContent = content.getText().toString();
                //Validate if textfield is empty or not
                if (title.length() != 0 && content.length() != 0) {
                    //Either save a new note or update existing note with new user input
                    if (chosenTitle != null && chosenContent != null) {
                        nDb.updateData(newTitle, newContent, chosenTitle, chosenContent);
                        Toast.makeText(this, "Note successfully edited",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        saveNote(newTitle, newContent);
                        title.setText("");
                        content.setText("");
                    }
                }
                else //Ensure all test fields is filled in
                {
                    if(content.length() == 0){
                        Toast.makeText(this, "You must provide content",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(title.length() == 0){
                        Toast.makeText(this, "You must provide a title",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            case R.id.actionDeleteNote:
                nDb.deleteData(chosenTitle, chosenContent);
                Toast.makeText(this, chosenTitle + " removed from database",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
        return super.onOptionsItemSelected(item);
    }
}
