# Sona3

**Best practice for Contract interface** <br>

**Contract interface** should only contains 3 interfaces :<br><br>
1- **View** : contains abstract functions related to the view, which its direction is from the Presenter to the View ex: update text / show or hide progress / ... etc. <br>
2- **Model** : contains abstract functions related to data ex: get data from the remote or locale Database. <br>
3- **Presenter** : contains abstract functions related to any events, which its direction is from the View to the Presenter ex: click button / destroy view / ... etc. <br><br>


**Then The Presenter class will take Contract.View as a parameter and implements both Contract.Presenter + Contract.Model**  <br><br>
**All business logic code will be only handled in the Main Presenter** <br><br>

[Reference](https://www.geeksforgeeks.org/mvp-model-view-presenter-architecture-pattern-in-android-with-example/)
