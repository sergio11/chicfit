# ChicFit: Your Personal AI Fashion Assistant 📸👗🤖

<img width="auto" height="200px" align="left" src="doc/main_logo.png" />

**ChicFit** is a revolutionary mobile application powered by AI, designed to offer you **personalized fashion advice** with just a snap! Capture photos of your clothes 📸 and let ChicFit’s **advanced AI** suggest the **perfect outfits** 👚👖, provide **style recommendations** 💡, and even guide you on whether your clothes are suitable for different **occasions** 🎉.

Whether you're preparing for a **casual day** 🏖️ or dressing up for a **special event** 🎂, ChicFit ensures you always look your best. It makes your **fashion decisions** smarter, easier, and more **confidence-boosting** than ever! 💃✨


<p align="center">
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
  <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/firebase-ffca28?style=for-the-badge&logo=firebase&logoColor=black" />
  <img src="https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white" />
  <img src="https://img.shields.io/badge/Google%20Gemini-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white" />
</p>

<p align="center">
  <img width="250px" src="doc/screenshots/previewed/image1.jpeg" />
  <img width="250px" src="doc/screenshots/previewed/image2.jpeg" />
  <img width="250px" src="doc/screenshots/previewed/image3.jpeg" />
  <img width="250px" src="doc/screenshots/previewed/image4.jpeg" />
  <img width="250px" src="doc/screenshots/previewed/image5.jpeg" />
</p>

## 🚀 Key Features

- **AI-Powered Fashion Advice** 🤖👗: Use ChicFit’s advanced AI to analyze your wardrobe and suggest stylish outfits based on your preferences and current trends.
- **Personalized Outfit Recommendations** 👚👖✨: Capture a photo of your clothes, and ChicFit will suggest the best outfit combinations tailored to your style and preferences.
- **Occasion-Based Suggestions** 🎉👗: Get tailored outfit ideas for different occasions, such as casual days, office wear, or special events.
- **Style Recommendations** 🧥✨: Receive expert fashion tips on how to accessorize and combine colors for a complete and stylish look.
- **Easy Wardrobe Management** 🧳📅: Organize your clothes and outfits and keep track of what’s in your wardrobe.
- **Enhanced User Experience** 🖥️✨: Designed with **Jetpack Compose**, the app delivers a seamless, modern UI with fluid animations and intuitive user flow.

## 🧠 How ChicFit Works

- **Firestore** 🔥📂: Stores personalized outfit suggestions, preferences, and details about your clothes, ensuring that each recommendation is perfectly tailored to you.
- **Firestorage** 🗄️📸: Stores the photos of your clothes, which are analyzed by AI to suggest the best possible combinations and provide style tips.
- **Gemini SDK** 🌟🤖: Powers the AI engine that analyzes images and generates outfit suggestions based on current trends and your personal style.
- **Image Analysis** 📸🔍: Analyzes your wardrobe images to understand the types of clothing and generate meaningful recommendations.

## 🔍 Home Screen Features

- **Outfit Suggestions List** 📄: View all your personalized outfit recommendations. Each suggestion includes details about the clothes and styling tips.
- **Search Functionality** 🔎: Easily search through your wardrobe items to find specific clothes, outfits, or styling tips.
- **Delete Functionality** 🗑️: Manage your wardrobe by deleting or removing unwanted items and suggestions.
- **Detailed View** 👁️: Access the details of each outfit suggestion, where you can adjust your look and finalize your outfit based on the AI recommendations.

## ✨ User Experience Enhancements

- **Optimized Performance** ⚡: Leveraging coroutines ensures smooth operations while processing outfit recommendations and image analysis.
- **Intuitive Navigation** 🧭: The UI, built with **Jetpack Compose**, offers a fluid and responsive experience that adapts to user input.
- **Dynamic Recommendations** 🎯: The combination of the Gemini SDK and MVI architecture ensures that outfit suggestions evolve based on your preferences, making every recommendation more personalized.

## 🛠️ Clean Architecture & MVI for a Robust Foundation

ChicFit is built on **Clean Architecture** principles, ensuring the application is scalable, maintainable, and easy to test. This architecture promotes a clear separation of concerns, with distinct layers for data, domain, and presentation, making it easier to manage and extend.

Using **Model-View-Intent (MVI)** as the UI architectural pattern, ChicFit ensures a reactive and unidirectional data flow that enhances the overall user experience and makes the app more responsive to user actions.

- **Domain-Centric Design** 🧩: The business logic is encapsulated in the domain layer, which interacts with data sources (repositories) and manages the flow of information to and from the UI.
- **Seamless Integration with Gemini SDK** 🤖🌐: The Clean Architecture approach ensures smooth integration with Gemini SDK, enabling fast and accurate image analysis for fashion recommendations.

## 🧰 Technologies Used

- **Kotlin** 💻: The primary programming language for Android development.
- **Jetpack Compose** 🖌️: A modern, declarative UI toolkit that allows for flexible and scalable UI design.
- **Gemini SDK** 🚀🤖: The core AI engine powering the image analysis and fashion recommendations.
- **Firestore** 🔥: Stores personalized outfit suggestions and wardrobe items.
- **Firestorage** 📸: Stores images of clothes that are analyzed by the AI to generate outfit suggestions.
- **Coroutines** ⏱️: Ensures efficient asynchronous task management for a smooth user experience.
- **Hilt/Dagger** 🛠️: Dependency injection frameworks for scalable architecture.
- **Retrofit** 🌐: Manages network communications with external APIs.
- **Coil** 🎨: Image loading library that integrates seamlessly with Jetpack Compose.

## Development with the Brownie UI Library

🍫 **Brownie**: [Jetpack Compose UI Library](https://github.com/sergio11/brownie_ui_library) 🚀

Brownie is a Jetpack Compose UI library that offers a set of pre-defined components to speed up Android app development. It helps developers apply best practices in screen state management and UI design.

### Features 🎉

- **Pre-defined Components**: Brownie provides a variety of ready-to-use components (buttons, lists, cards) to help you design attractive and consistent user interfaces.
- **State Management**: Supports screen state management with MVI or MVVM patterns.
- **Customization**: Highly customizable components that can be adapted to any app's visual style.
- **Jetpack Compose Compatibility**: Fully integrated with Jetpack Compose to ensure optimal performance.

### Model-View-Intent (MVI) Architecture 🏗️

Brownie encourages using MVI for efficient screen state management. In this architecture:

- **Model**: Represents the UI state, with `BrownieViewModel` handling the state.
- **View**: The UI renders based on the state provided by the ViewModel, using Brownie's pre-defined components.
- **Intent**: User actions that trigger state changes, like button clicks or list selections.

For more information, check out the [Brownie UI Library on GitHub](https://github.com/sergio11/brownie_ui_library) and give it a ⭐ if you like it!

## App Screenshots

Here are some screenshots to showcase the app's design and features.

<p>
  <img width="250px" loading="lazy" src="doc/screenshots/picture_5.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_6.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_7.png" />
</p>

<p>
  <img width="250px" loading="lazy" src="doc/screenshots/picture_8.png" />
   &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_9.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_14.png" />
</p>

<p>
  <img width="250px" loading="lazy" src="doc/screenshots/picture_15.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_16.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_17.png" />
</p>

<p>
  <img width="250px" loading="lazy" src="doc/screenshots/picture_13.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_10.png" />
  &nbsp;&nbsp;&nbsp;
   <img width="250px" loading="lazy" src="doc/screenshots/picture_11.png" />
</p>

<p>
  <img width="250px" loading="lazy" src="doc/screenshots/picture_12.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_18.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_19.png" />
</p>

<p>
  <img width="250px" loading="lazy" src="doc/screenshots/picture_1.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_2.png" />
  &nbsp;&nbsp;&nbsp;
  <img width="250px" loading="lazy" src="doc/screenshots/picture_4.png" />
</p>

<p>
  <img width="250px" loading="lazy" src="doc/screenshots/picture_3.png" />
</p>


## Contribution
Contributions to ChicFit are highly encouraged! If you're interested in adding new features, resolving bugs, or enhancing the project's functionality, please feel free to submit pull requests.

## Credits
ChicFit is developed and maintained by Sergio Sánchez Sánchez (Dream Software). Special thanks to the open-source community and the contributors who have made this project possible. If you have any questions, feedback, or suggestions, feel free to reach out at dreamsoftware92@gmail.com.

## Acknowledgements 🙏

 - Special thanks to **technophilist** for the Perceive app, which served as a source of inspiration for this project. Visit [Perceive](https://github.com/technophilist/Perceive).

- We express our deep appreciation to [Freepik](https://www.freepik.es/) for generously providing the resources used in this project.

- <div> Icons and images takes from <a href="https://www.freepik.com" title="Freepik"> Freepik </a> from <a href="https://www.flaticon.es/" title="Flaticon">www.flaticon.es'</a></div>
- Template mockup from https://previewed.app/template/AFC0B4CB

 ## Visitors Count

 <img width="auto" src="https://profile-counter.glitch.me/chicfit/count.svg" />

 ## Please Share & Star the repository to keep me motivated.
  <a href = "https://github.com/sergio11/chicfit/stargazers">
     <img src = "https://img.shields.io/github/stars/sergio11/chicfit" />
  </a>

## License ⚖️

This project is licensed under the MIT License, an open-source software license that allows developers to freely use, copy, modify, and distribute the software. 🛠️ This includes use in both personal and commercial projects, with the only requirement being that the original copyright notice is retained. 📄

Please note the following limitations:

- The software is provided "as is", without any warranties, express or implied. 🚫🛡️
- If you distribute the software, whether in original or modified form, you must include the original copyright notice and license. 📑
- The license allows for commercial use, but you cannot claim ownership over the software itself. 🏷️

The goal of this license is to maximize freedom for developers while maintaining recognition for the original creators.

```
MIT License

Copyright (c) 2025 Dream software - Sergio Sánchez 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
