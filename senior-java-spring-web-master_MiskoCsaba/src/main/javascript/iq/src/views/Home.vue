<template>
  <div class="home">
    <vue-single-upload class="file-upload"
                       ref="singleUploadRef"
                       @finished="onUploadFinished()">
    </vue-single-upload>

    <signature-pad ref="signaturePadRef" ></signature-pad>

    <button type="submit" @click="save">Save Signed Image</button>

    <image-list class="mt-5" ref="imagelist"></image-list>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import VueSingleUpload from '@/components/VueSingleUpload.vue';
import ImageList from '@/components/ImageList.vue'; // @ is an alias to /src
import SignaturePad from '@/components/SignaturePad.vue';
import axios from "axios/index";

@Component({
  components: {
    VueSingleUpload,
    ImageList,
    SignaturePad
  },
})
export default class Home extends Vue {

  public fileName: string | null = null;
  public thumbnailUrl: string | null = null;

  @Prop() private msg!: string;
  @Prop() private showDetails!: boolean | undefined;

  private async onUploadFinished() {
    console.log("UploadFinished");
    (this.$refs.imagelist as ImageList).refresh();
  }

  async save(): Promise<void> {
    console.log("stepped in home save method")
    try {
      // Get the file from the Dropzone component
      const file = (this.$refs.singleUploadRef as VueSingleUpload).getFile();

      // Get the signature string from the signature pad component
      const signature = (this.$refs.signaturePadRef as SignaturePad).save();

      // Build a form data object to send to the server
      const formData = new FormData();
      formData.append('file', file);
      formData.append('signature', signature);

      console.log('This is the file dara size: ' + file?.size + ' and type: ' + file?.type)
      console.log('This is the signature: ' + signature)

      // Make a POST request to the server to save the file and signature
      const response = await axios.post('http://localhost:8080/api/file', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      // Check if the save was successful
      if (response.status === 200) {
        console.log('File and signature saved successfully');
      } else {
        console.error(`Error saving file and signature. Status: ${response.status}`);
      }
    } catch (error) {
      console.error(error);
    }
  }

}
</script>

<style lang="scss">
.home {
  .uploader-dropzone {
    background-color: aliceblue;
  }
}
</style>
