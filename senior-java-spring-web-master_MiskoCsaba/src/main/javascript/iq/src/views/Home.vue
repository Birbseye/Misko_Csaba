<template>
  <div class="home">
    <vue-single-upload class="file-upload"
                       ref="singleUploadRef"
    >
    </vue-single-upload>

    <signature-pad ref="signaturePadRef"></signature-pad>

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
        this.$notify({
          group: 'notify',
          type: 'success',
          title: 'SUCCESS!✅',
          text: 'The Image Successfully Uploaded!',
          duration: 4000,
        });
        await (this.$refs.imagelist as ImageList).refresh();
        (this.$refs.signaturePadRef as SignaturePad).clear();
        (this.$refs.singleUploadRef as VueSingleUpload).removeFiles();
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
