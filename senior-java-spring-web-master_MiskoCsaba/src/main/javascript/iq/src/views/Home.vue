<template>
  <div class="home">
    <vue-single-upload class="file-upload"
                       ref="singleUploadRef"
                       >
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
import {FetchHttpService, HttpService} from '@/services/http-service';
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
  private httpService: HttpService

  @Prop() private msg!: string;
  @Prop() private showDetails!: boolean | undefined;


  constructor() {
    super();

    this.httpService = new FetchHttpService();
  }

  async save(): Promise<void> {
    try {
      const file = (this.$refs.singleUploadRef as VueSingleUpload).getFile();

      const signature = (this.$refs.signaturePadRef as SignaturePad).getSignature();

      const formData = new FormData();
      formData.append('file', file);
      formData.append('signature', signature);

      const response = await axios.post('http://localhost:8080/api/file', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      if (response.status === 200) {
        alert("Image uploaded successfully!")
        console.log('File and signature saved successfully');
        await (this.$refs.imagelist as ImageList).refresh();
        (this.$refs.signaturePadRef as SignaturePad).clear();
        (this.$refs.singleUploadRef as VueSingleUpload).removeFiles();
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
